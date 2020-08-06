package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.ProductDao;
import com.fh.entity.po.Area;
import com.fh.entity.po.Product;
import com.fh.service.ProductService;
import com.fh.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    // 查询热销商品
    @Override
    public List<Product> queryHotProduct() {
        return productDao.queryHotProduct();
    }

    // 根据typeId查询所有商品
    @Override
    public List<Product> queryAllProductByTypeId(Integer id) {
        return productDao.queryAllProductByTypeId(id);
    }

    // 查询商品详情
    @Override
    public Product queryProductDetail(Integer proId) {
        Product product = productDao.queryProductDetail(proId);
        // 将地区的code码 转为中文
        String[] areaIds = product.getAreaIds().split(",");
        StringBuffer sb=new StringBuffer();
        Jedis jedis = RedisUtils.getJedis();
        for (int i = 0; i <areaIds.length; i++) {
            // 当数据库里查出-1时，直接跳出此循环
            String areaJsonString = jedis.hget("area_cjj", areaIds[i]);//想想为啥用redis   地区数据不经常变动，而且还会经常使用
            if(areaJsonString==null){
                break;
            }
            //将字符串转为area对象
            Area area = JSONObject.parseObject(areaJsonString, Area.class);
            //String areaName = productDao.queryAreaNameById(areaIds[i]);
            sb.append(area.getName()).append(" ");
        }
        RedisUtils.returnJedis(jedis);
        product.setAreaIds(sb.toString());
        return product;
    }
}
