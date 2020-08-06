package com.fh.service.impl;

import com.fh.dao.TypeDao;
import com.fh.entity.po.Type;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> queryTypeList() {
        return typeDao.selectList(null);
    }
}
