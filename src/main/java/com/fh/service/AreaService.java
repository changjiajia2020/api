package com.fh.service;

import com.fh.entity.po.Area;

import java.util.List;

public interface AreaService {

    // 根据id查询地区名称
    String queryAreaNameById(String id);

    // 查询地区所有数据
    List<Area> queryAreaList();
}
