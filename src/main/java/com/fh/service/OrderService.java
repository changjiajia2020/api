package com.fh.service;

import com.fh.common.exception.NotEnoughStockException;
import com.fh.entity.po.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     *  当前方法抛异常的方式：serviceImpl中，抛异常(向上)，则对应的service中的相应方法中也要抛异常
     *  原理：接口的特点
     *  抛异常的两种方式：
     *      1. try catch 进行处理(但它对应的service中的相应方法中不用抛异常)
     *      2. 向上抛（如果serviceImpl中，要抛异常，则它对应的service中的相应方法中也要抛异常）
     */

    /**
     *  抛异常
     *      应用场景：超卖问题
     *          若结算的所有商品中，第一件商品的库存够，可以执行成功，而且更新了修改语句
     *      但是：第二件商品库存不够，如果直接返回map信息，但是第一条数据  数据库中的库存已经减少了，其实此订单并没有执行成功
     *     所以：应该抛异常   涉及到事务的原子性   要么全部执行成功，要么全部执行失败
     *     抛出异常，则会导致事务回滚
     */
    // 创建订单
    // 订单创建成功之后，将订单号返回，总计，订单返回   所以用map吗？？？？  原因如下
    // 根据需求，因为它可能返回多个字段，所以可以用map或者JavaBean  但是，由于：不确定返回什么信息(字段),用map灵活，可以放任何值
    Map createOrder(Integer addressId,Integer payType) throws NotEnoughStockException;

    // 提交订单之后，生成的随机二维码TwodimensionalCode图片
    Map createRandomTCodePhoto(Integer orderId) throws Exception;

    // 查询支付状态   根据订单id查询支付状态    返回值类型是Integer  因为：枚举里面已经定义了数字
    Integer queryPayStatus(Integer orderId) throws Exception;

    // 查询数据条数

    // 查询订单
    List<Order> queryOrder();

}
