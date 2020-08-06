package com.fh.service;

import com.fh.entity.po.Cart;

import java.util.List;

public interface CartService {

    //  将商品加入购物车
    Integer addProductToCart(Integer proId,Integer count);

    // 查询购物车中所有商品
    List queryCartListByUser();

    // 从购物车中删除商品
    Long deleteCartFromProduct(Integer proId);

    // 更新购物车中商品的状态
    void updateProductCartState(String pids);

    // 获取购物车中选中商品的信息   不需要传参数吗？？？？？
    List<Cart> queryCartCheckedProduct();
}
