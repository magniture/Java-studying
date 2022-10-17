package com.linkedbear.architecture.h_decorator.service.impl;

import com.linkedbear.architecture.h_decorator.dao.DemoDao;
import com.linkedbear.architecture.h_decorator.factory.BeanFactory;
import com.linkedbear.architecture.h_decorator.service.DemoService;

import java.util.List;

public class DemoServiceImpl implements DemoService {
    
    DemoDao demoDao = (DemoDao) BeanFactory.getBean("demoDao");
    
    @Override
    public List<String> findAll() {
        return demoDao.findAll();
    }
    
    @Override
    public int add(String userId, int points) {
        return points;
    }
    
    @Override
    public int subtract(String userId, int points) {
        return points;
    }
    
    @Override
    public int multiply(String userId, int points) {
        return points;
    }
    
    @Override
    public int divide(String userId, int points) {
        return points;
    }
}
