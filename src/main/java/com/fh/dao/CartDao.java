package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CartDao extends BaseMapper<Cart> {

    // 根据id查询单个商品
    Cart queryCartById(Integer pid);



}
