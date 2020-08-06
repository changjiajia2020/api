package com.fh.service.impl;

import com.fh.dao.RegisterDao;
import com.fh.entity.po.Register;
import com.fh.entity.po.User;
import com.fh.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;

    // 根据手机号查询
    @Override
    public Register findByIphone(String iphone) {
        return registerDao.findByIphone(iphone);
    }

    // 根据用户名查询
    @Override
    public Register findByName(String name) {
        return registerDao.findByName(name);
    }

}
