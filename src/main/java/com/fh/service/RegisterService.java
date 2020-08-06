package com.fh.service;

import com.fh.entity.po.Register;

public interface RegisterService {

    // 根据手机号查询
    Register findByIphone(String iphone);

    // 根据用户名查询
    Register findByName(String name);
}
