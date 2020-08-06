package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.enums.PayStatusEnum;
import com.fh.common.exception.NotEnoughStockException;
import com.fh.dao.OrderDao;
import com.fh.dao.ProductDao;
import com.fh.entity.po.*;
import com.fh.service.OrderService;
import com.fh.utils.redis.RedisUse;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 一般，serviceImpl注入时，用@Resources
     *       controller注入时，用@Autowired
     */
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ProductDao productDao;

    // 创建订单表
    @Override
    public Map createOrder(Integer addressId, Integer payType) throws NotEnoughStockException {
        /**
         * 1. 实例化map  返回给前台状态码
         * 2. 实例化集合   用来存放订单详情表的信息
         * 3. 完善数据  保存到数据库
         * 4. 设置商品的清单个数 (要结算的个数)
         * 5. 设置总金额
         * 6. 保存订单详情表
         */
        Map map = new HashMap();
        List<OrderProduct> list=new ArrayList<>();  // 可能会有好几个订单
        // 完善订单表  保存到数据库
        Order order = new Order();
        order.setAddressId(addressId);
        order.setPayType(payType);
        order.setCreateDate(new Date());
        // 支付状态 涉及到多种状态，所以：要创建一个枚举
        order.setPayType(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        //设置商品的清单个数   商品的清单怎么拿(存储在redis中)  前提是 库存里够
        Integer typeCount=0;
        //设置总金额
        BigDecimal totalMoney=new BigDecimal(0);
        // 获取购物车的key(从redis中获取)   登录信息    从request中拿
        // <!--只有经过拦截器 才能将"login_user"放在request中，其他地方才能取出来-->
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();

/**
 *  输入指令   hgetall cart_13453511561_cjj
 * "125"
 *  "{\"count\":2,\"id\":125,\"imgPath\":\"https://fh1908a.oss-cn-qingdao.aliyuncs.com/40b81cb4-c68e-4e9e-975a-f53391bc1a00.png\",\"name\":\"zxc123\",\"price\":12,\"state\":true,\"subtotal\":24}"
 *  "120"
 *  "{\"count\":8,\"id\":120,\"imgPath\":\"https://fh1908a.oss-cn-qingdao.aliyuncs.com/3c3d1f16-72b5-4543-a7b1-8c51cc1b24c9.png\",\"name\":\"111\",\"price\":11,\"state\":false,\"subtotal\":88}"
 */

        //获取购物车  商品信息是string  全部的  （要的是选中的）
        List<String> productString = RedisUse.hvals("cart_" + iphone + "_cjj");
        for (int i = 0; i < productString.size(); i++) {
            // 将字符串转化成javaBean对象    get(i)获取每一个吗？？？？
            Cart cart = JSONObject.parseObject(productString.get(i), Cart.class);
            // 判断是否为订单中的商品  之前不是已经判断过了吗   true  false
            if(cart.isState()==true){
                // 查询数据库的信息
                Product product = productDao.selectById(cart.getId());  // 咋是购物车id
                if(product.getStock()>=cart.getCount()){
                    // 说明：库存够   1. 订单的商品个数+1  钱数  减库存
                    typeCount++;
                    totalMoney=totalMoney.add(cart.getPrice());
                    // 维护订单详情表  将数据增加到订单详情表
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setCount(cart.getCount());
                    orderProduct.setProductId(cart.getId());
                    list.add(orderProduct);
                    // 减库存  //更新语句
                    //product.setStock(product.getStock()-cart.getCount());
                    //productDao.updateById(product);

                    //减库存  数据库的锁 保证不会超卖  update  返回一个值 int
                    int i1 = productDao.updateProductCount(product.getProId(), cart.getCount());
                    if(i1==0){//超卖
                        throw new NotEnoughStockException("商品编号为:"+cart.getId()+"的库存不足   库存只有："+product.getStock());
                    }
                }else{  //库存不够  什么是事务  事务的原子性
                    throw new NotEnoughStockException("商品编号为:"+cart.getId()+"的库存不足   库存只有："+product.getStock());
                }
            }
        }
        // 商品种类的数量
        order.setProTypeCount(typeCount);
        // 小计
        order.setSubtotal(totalMoney);
        orderDao.insert(order);
        // 返回订单编号
        // 删除redis中的数据  把购物车中已经结算的商品 移除redis
        for (int i = 0; i < productString.size(); i++) {
            //将字符串转换为javabean
            Cart cart = JSONObject.parseObject(productString.get(i), Cart.class);
            if(cart.isState()==true){
                RedisUse.hdel("cart_" + iphone + "_cjj",cart.getId()+"");
            }
        }
        map.put("code",200);
        map.put("orderId",order.getId());
        map.put("totalMoney",totalMoney);
        return map;
    }

    // 提交订单之后，生成的随机二维码TwodimensionalCode图片
    @Override
    public Map createRandomTCodePhoto(Integer orderId) throws Exception {
        Map rs = new HashMap();
        /**
         * selectById()     // mybatis_plus  查询单条数据
         *    主要目的：获取金额 (从数据库中查金额)，不能接受页面传过来的
         *      防止有人篡改页面上的数据
         */

        /**
         *  redis应用场景
         *      优化：订单生成之后，将url和订单号存到redis中
         *          例如：当用户生成订单之后，但是没有付钱，这时：直接将二维码的url和订单号存储在redis中，
         *              当用户在规定时间内付款，则直接从redis中取，减少数据库的压力
         *
         */
        //从redsi中判断是否已经生成过
        String meonyPhotoUrl = RedisUse.get("order_"+orderId+"_cjj");
        if(StringUtils.isEmpty(meonyPhotoUrl)!=true){//不为空  已经生成过二维码
            rs.put("code",200);
            rs.put("url",meonyPhotoUrl);
            return rs;
        }
        Order order = orderDao.selectById(orderId);
        // 微信支付  natvie   商户生成二维码
        //配置配置信息
        FeiConfig config = new FeiConfig();
        //得到微信支付对象
        WXPay wxpay = new WXPay(config);
        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        /**
         * "body"       "fee_type"      "time_expire"       "total_fee"     "notify_url"    "out_trade_no"
         *      引号中的内容，都是固定写法！
         * "飞狐电商666-订单支付"  中的内容，只能由字母，数字，下划线组成
         */
        //对订单信息描述
        data.put("body", "飞狐电商666-订单支付");
        //String payId = System.currentTimeMillis()+"";  id+System.currentTimeMillis()
        // String s = "weixin1_order_" + 1 + System.currentTimeMillis();
        //设置订单号 （保证唯一 ）
        data.put("out_trade_no","weixin1_order_cjj_"+orderId);
        //设置订单金额   单位分
// 正确写法：data.put("total_fee",order.getSubtotal()+"");   // 因为subtotal是bigDecimal  但此处必须写string类型，所以：要+""
        //设置币种
        data.put("fee_type", "CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire", dateStr);
        //设置订单金额   单位分
        data.put("total_fee","1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        // 统一下单     unifiedOrder()   调用第三方接口  所以：必须连网！！
        Map<String, String> resp = wxpay.unifiedOrder(data);
        System.out.println(orderId+"下订单结果为:"+ JSONObject.toJSONString(resp));
        // "SUCCESS"    "return_code"   "SUCCESS"   "result_code"  "code_url"   "return_msg"  接口文档里规定的
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code")) && "SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            // equalsIgnoreCase  忽略大小写
            rs.put("code",200);
            rs.put("url",resp.get("code_url"));
            // 更新订单状态   订单已经生成，二维码也生成了  生成订单时，设置的是未支付   由未支付--->待支付(因为：二维码已经生成了，接下来就是用户扫码的过程)
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderDao.updateById(order);
            //将二维码存入redis  设置失效时间                               30分钟
            RedisUse.set("order_"+orderId+"_cjj",resp.get("code_url"),30*60);
        }else{
            rs.put("code",600);
            rs.put("info",resp.get("return_msg"));
        }
        return rs;
    }

    // 查询支付状态
    @Override
    public Integer queryPayStatus(Integer orderId) throws Exception {
        FeiConfig config = new FeiConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no","weixin1_order_cjj_"+orderId);
        // 查询支付状态
        Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){//支付成功
                //更新订单状态
                Order order=new Order();
                order.setId(orderId);
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderDao.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }

    // 查询订单
    @Override
    public List<Order> queryOrder() {
        // mybatis_plus
        return orderDao.selectList(null);
    }


}
