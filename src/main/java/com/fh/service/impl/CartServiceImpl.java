package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.CartDao;
import com.fh.dao.ProductDao;
import com.fh.entity.po.Cart;
import com.fh.entity.po.Product;
import com.fh.entity.po.Register;
import com.fh.service.CartService;
import com.fh.utils.redis.RedisUse;
import com.fh.utils.redis.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private HttpServletRequest request;

    // 将商品加入到购物车中
    @Override
    public Integer addProductToCart(Integer proId, Integer count) {
        // 将数据存入redis   hash  key 用户的唯一标示  filed 商品id  value  商品信息
        // 判断库存是否够  selectById()查询单个对象   使用的是：mybatis_plus ！！！
        /**
         * count>0  应用场景：购物车页面，用户点击到>0时，再判断库存是否够
         * 不大于0  直接往下执行
         */
        if(count>0){
            Product product = productDao.selectById(proId);
            if(count > product.getStock()){ // 当用户在前台输入的数量>库存时,返回一个负数
                return product.getStock()-count;
            }
        }


        // 获取用户信息   "login_user"和登录拦截器中定义的保持一致
/*        Map login_user = (Map) request.getAttribute("login_user");
        String iphone = (String) login_user.get("iphone");*/
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();
        // Register register = new Register();
        // 获取购物车中  指定商品的信息
        String proInfo = RedisUse.hget("cart_" + iphone + "_cjj", proId + "");
        if(StringUtils.isEmpty(proInfo)){
            // 说明：进行新增
            //查询商品信息
            // ProductCart productCart = productDao.queryProductCartById(proId);
            Cart cart = cartDao.queryCartById(proId);
            // 设置默认状态为选中
            cart.setId(proId);  // 删除时，将id存进去   否则：会出现undefined现象
            cart.setState(true);
            cart.setCount(count);
            //计算小计
            BigDecimal subtotal = cart.getPrice().multiply(new BigDecimal(count));
            cart.setSubtotal(subtotal);
            //将商品信息 转成json字符串
            String productCartString = JSONObject.toJSONString(cart);
            //将数据放入redis
            RedisUse.hset("cart_"+iphone+"_cjj",proId+"",productCartString);
        }else{
            //存在购物车中  所以：只需要修改个数和小计即可
            Cart cart = JSONObject.parseObject(proInfo, Cart.class);
            // 修改个数
            cart.setCount(cart.getCount()+count);
            // 判断库存是否够      购物车页面如果一直点新增   这时，就要判断数据库中的库存是否
            Product product = productDao.selectById(proId);//数据库的数量
/*            if(cart.getCount()>product.getStock()){//判断库存是否够
               // return product.getStock()-cart.getCount();
                cart.setCount(product.getStock());
            }else{
                // 如果减到0   则要提示
                if(cart.getCount()<1){
                    return 1;
                }

            }*/
            if(cart.getCount()>product.getStock()){//判断库存是否够
                return product.getStock()-cart.getCount();
            }

            if(cart.getCount()<1){
                return 1;
            }
      /*
           两种实现方式
            if(cart.getCount()<1){
                cart.setCount(1);
            }
       */
            //计算小计
            BigDecimal subtotal = cart.getPrice().multiply(new BigDecimal(cart.getCount()));
            cart.setSubtotal(subtotal);
            //将商品信息 转成json字符串
            String productCartString = JSONObject.toJSONString(cart);
            //将数据放入redis
            RedisUse.hset("cart_"+iphone+"_cjj",proId+"",productCartString);

        }

        //怎么获取商品总量的个数
        long hlen = RedisUse.hlen("cart_" + iphone + "_cjj");
        return (int)hlen;
    }

    // 查询购物车中所有商品
    @Override
    public List queryCartListByUser() {
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();
        // 获取购物车的所有数据
        List<String> carts = RedisUse.hvals("cart_" + iphone + "_cjj");
        return carts;
    }

    // 从购物车中删除商品
    @Override
    public Long deleteCartFromProduct(Integer proId) {
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();

        Jedis jedis = RedisUtils.getJedis();

        jedis.hdel("cart_"+iphone+"_cjj",proId.toString());

        long hlen = RedisUse.hlen("cart_" + iphone + "_cjj");
        return hlen;
    }

    // 更新购物车中商品的状态
    @Override
    public void updateProductCartState(String pids) {
        /**
         * 查询当前用户的所有购物车数据
         *      1. 获取用户数据
         *      2. 获取购物车的所有数据
         */
        // 获取用户数据
/*        Map login_user = (Map) request.getAttribute("login_user");
        String iphone = (String) login_user.get("iphone");*/
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();
        // 获取购物车的所有数据
        List<String> carts = RedisUse.hvals("cart_" + iphone + "_cjj");
        for (int i = 0; i < carts.size(); i++) {
            // 商品的json字符串
            String cartStr = carts.get(i);
            // 将商品信息转为对象
            Cart cart = JSONObject.parseObject(cartStr, Cart.class);

            /**
             * 前台   1,2,4,   true
             * 购物车  1,2,3,4  true
             * 所以  应该将redis中id为3的，改为false
             */
            // 判断此商品是否在选中的ids中
            Integer id = cart.getId();
            if((","+pids).contains(","+id+",")==true){
                // 说明：在   则不需要修改状态
                cart.setState(true);
                RedisUse.hset("cart_" + iphone + "_cjj",cart.getId()+"",JSONObject.toJSONString(cart));
            }else{
                // 说明：不在   则需要修改状态
                cart.setState(false);
                RedisUse.hset("cart_" + iphone + "_cjj",cart.getId()+"",JSONObject.toJSONString(cart));
            }
        }
    }

    // 获取购物车中选中商品的信息
    @Override
    public List<Cart> queryCartCheckedProduct() {
        /**
         * 从redis中取出所有购物车的数据(因为：购物车中所有信息，都存在redis中)
         *      A. 获取登录人信息
         *      B. 获取手机号(唯一标识)
         *
         */
        // 获取登录人信息
        /*Map login_user = (Map) request.getAttribute("login_user");
        String iphone = (String) login_user.get("iphone");*/
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();
        // 获取购物车中的所有数据
        List<String> cartList = RedisUse.hvals("cart_13453511561_cjj");
        // 实际需求的数据
        List<Cart> list = new ArrayList<>();
        // 处理想要的数据
        for (int i = 0; i < cartList.size(); i++) {
            // 为啥是字符串啊？   不应该是hash吗
            /**     输入指令：hvals cart_13453511561_cjj
             * "{\"count\":2,\"id\":125,\"imgPath\":\"https://fh1908a.oss-cn-qingdao.aliyuncs.com/40b81cb4-c68e-4e9e-975a-f53391bc1a00.png\",\"name\":\"zxc123\",\"price\":12,\"state\":true,\"subtotal\":24}"
             */
            String productStr = cartList.get(i);
            // 将字符串转换为JavaBean对象
            Cart cart = JSONObject.parseObject(productStr,Cart.class);
            // 获取选中的商品数据
            if(cart.isState()==true){
                // 说明被选中
                list.add(cart);
            }

        }
        return list;
    }
}
