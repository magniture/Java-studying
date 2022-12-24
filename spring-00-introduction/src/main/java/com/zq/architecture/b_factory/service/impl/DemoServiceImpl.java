package com.zq.architecture.b_factory.service.impl;

import com.zq.architecture.b_factory.dao.DemoDao;
import com.zq.architecture.b_factory.factory.BeanFactory;
import com.zq.architecture.b_factory.service.DemoService;

import java.util.List;

public class DemoServiceImpl implements DemoService {
    
    DemoDao demoDao = BeanFactory.getDemoDao();
    
    @Override
    public List<String> findAll() {
        return demoDao.findAll();
    }
}
