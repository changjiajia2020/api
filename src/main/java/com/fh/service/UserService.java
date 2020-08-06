package com.fh.service;

import com.fh.entity.po.User;

import java.util.List;

public interface UserService {

    // 查询所有数据
    List<User> queryUserList();

    // 根据手机号查询用户名
    String userName(String inputIphone);
}
