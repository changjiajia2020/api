package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.Register;
import com.fh.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RegisterDao extends BaseMapper<Register> {

    // 根据手机号查询
    Register findByIphone(String iphone);

    // 根据用户名查询
    Register findByName(String name);
}
