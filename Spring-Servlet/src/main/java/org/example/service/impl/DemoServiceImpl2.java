package org.example.service.impl;

import org.example.dao.DemoDao;
import org.example.factory.BeanFactory;
import org.example.service.DemoService;

import java.util.List;

public class DemoServiceImpl2 implements DemoService {

    DemoDao demoDao = BeanFactory.getDemoDao();

    @Override
    public List<String> findAll() {
        return demoDao.findAll();
    }
}
