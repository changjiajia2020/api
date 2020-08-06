package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 识别到dao的路径，两种方式：
 *  1. 在dao层加入两个注解@Repository和@Mapper
 *  2. 在APP启动类上，加@MapperScan("com.fh.dao")注解
 */
@Repository
@Mapper
public interface TypeDao extends BaseMapper<Type> {
}
