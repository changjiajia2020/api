package com.fh.dao;

import com.fh.entity.po.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AreaDao {

    // 根据id查询地区名称
    String queryAreaNameById(String id);

    // 查询地区所有数据
    List<Area> queryAreaList();
}
