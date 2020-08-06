package com.fh.controller;

import com.fh.annotation.annotationByLog.LogFileds;
import com.fh.common.JsonData;
import com.fh.common.exception.NotEnoughStockException;
import com.fh.entity.po.Order;
import com.fh.service.OrderService;
import com.fh.utils.redis.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController // 相当于:@ResponseBody + @Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @RequestMapping("createOrder")
    public JsonData createOrder(Integer addressId, Integer payType, String flag) throws NotEnoughStockException {
        //处理接口幂等性    同一个请求 发送多次   结果只处理一次
        boolean exists = RedisUse.exists(flag);//判断redis是否存在key
        if(exists==true){//二次请求
            return JsonData.getJsonError(300,"请求处理中");
        }else{
            // 将flag作为key
            RedisUse.set(flag,"",10);
        }
        Map order = orderService.createOrder(addressId, payType);
        return JsonData.getJsonSuccess(order);
    }

    // 提交订单之后，生成的随机二维码TwodimensionalCode图片
    @RequestMapping("createRandomTCodePhoto")
    public JsonData createRandomTCodePhoto(Integer orderId) throws Exception{
        Map randomTCodePhoto = orderService.createRandomTCodePhoto(orderId);
        return JsonData.getJsonSuccess(randomTCodePhoto);
    }

    // 1 支付成功  2 支付中  3 未支付
    @RequestMapping("queryPayStatus")
    @LogFileds("查询支付状态")    // 日志管理
    public JsonData queryPayStatus(Integer orderId) throws Exception{
        Integer status = orderService.queryPayStatus(orderId);
        return JsonData.getJsonSuccess(status);
    }
    
    // 查询订单
    @RequestMapping("queryOrder")
    public JsonData queryOrder() {
        List<Order> orderList = orderService.queryOrder();
        return JsonData.getJsonSuccess(orderList);
    }

}
