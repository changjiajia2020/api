package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.entity.po.Product;
import com.fh.service.ProductService;
import com.fh.utils.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@Api(description="商品管理")
@RestController
@RequestMapping("product")
// @CrossOrigin("http://localhost:8085")   // 解决跨域问题
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查询热销商品   存储到redis中
    @RequestMapping("queryHotProduct")
    @ApiOperation("查询热销商品")
    public JsonData queryHotProduct(){
        Jedis jedis = RedisUtils.getJedis();
        String hot_product = jedis.get("hot_product_springboot");
        if(StringUtils.isEmpty(hot_product)){
            // 说明：是空的
            List<Product> list = productService.queryHotProduct();
            hot_product = JSONObject.toJSONString(list);    // toJSONString 把对象转为String
            jedis.set("hot_product_springboot",hot_product);
            RedisUtils.returnJedis(jedis);
            return JsonData.getJsonSuccess(list);   // 这括号里，应该写哪个呀 hot_product
        }
        
        return JsonData.getJsonSuccess(hot_product);

    }

    // 根据typeId查询所有商品
    @RequestMapping("queryAllProductByTypeId")
    @ApiOperation("根据typeId查询所有商品")
    public JsonData queryAllProductByTypeId(Integer id){
        List<Product> list = productService.queryAllProductByTypeId(id);
        return JsonData.getJsonSuccess(list);
    }

    // 查询商品详情
    @RequestMapping("queryProductDetail")
    @ApiOperation("查询商品详情")
    public JsonData queryProductDetail(Integer proId){
        Product product = productService.queryProductDetail(proId);
        return JsonData.getJsonSuccess(product);
    }

}
