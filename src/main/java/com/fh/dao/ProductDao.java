package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductDao extends BaseMapper<Product> {

    // 查询热销商品
    @Select("select proId,name,imgPath,price from shop_product2 where isHot=1")
    List<Product> queryHotProduct();

    // 根据typeId查询所有商品
    List<Product> queryAllProductByTypeId(Integer id);

    // 查询商品详情
     Product queryProductDetail(Integer proId);

    // 修改商品数量   传递多个值   1  javabean  2 map  3 @param
    // 产品id   更新的数量
    int updateProductCount(@Param("id") Integer pid, @Param("count") Integer count);

}
