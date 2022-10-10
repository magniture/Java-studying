package org.example.service.impl;

import org.example.dao.DemoDao;
import org.example.factory.BeanFactory;
import org.example.factory.BeanFactory2;
import org.example.service.DemoService;

import java.util.List;

public class DemoServiceImpl3 implements DemoService {
    DemoDao demoDao = (DemoDao) BeanFactory2.getBean("demoDao");

    public DemoServiceImpl3() {
        for (int i = 0; i < 10; i++) {
            System.out.println(BeanFactory2.getBean("demoDao"));
        }
    }

    @Override
    public List<String> findAll() {
        return demoDao.findAll();
    }
}
