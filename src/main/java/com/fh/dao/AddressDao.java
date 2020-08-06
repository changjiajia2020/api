package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * mybatis_plus
 */
@Repository
@Mapper
public interface AddressDao extends BaseMapper<Address> {
}
