package com.fh.service;

import com.fh.entity.po.Product;

import java.util.List;

public interface ProductService {

    // 查询热销商品
    List<Product> queryHotProduct();

    // 根据typeId查询所有商品
  //  List<Product> queryAllProductByTypeId(String typeId);

    // 根据typeId查询所有商品
    List<Product> queryAllProductByTypeId(Integer id);

    // 查询商品详情
    Product queryProductDetail(Integer proId);
}
