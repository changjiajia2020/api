package com.fh.service.impl;

import com.fh.dao.UserDao;
import com.fh.entity.po.User;
import com.fh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    // 查询所有数据
    @Override
    public List<User> queryUserList() {
        return userDao.selectList(null);
    }

    // 根据手机号查询用户名
    @Override
    public String userName(String inputIphone) {
        return null;
    }
}
