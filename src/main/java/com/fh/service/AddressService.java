package com.fh.service;

import com.fh.entity.vo.AddressInfo;

import java.util.List;

public interface AddressService {

    // 查询地区的详细信息    只查询出页面需要展示的数据即可
    List<AddressInfo> queryAddressInfoList();
}
