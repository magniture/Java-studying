package com.zq.architecture.b_factory.factory;

import com.zq.architecture.b_factory.dao.DemoDao;
import com.zq.architecture.b_factory.dao.impl.DemoMySQLDao;
import com.zq.architecture.b_factory.service.DemoService;
import com.zq.architecture.b_factory.service.impl.DemoServiceImpl;

public class BeanFactory {
    
    public static DemoService getDemoService() {
        return new DemoServiceImpl();
    }
    
    public static DemoDao getDemoDao() {
//        return new DemoDaoImpl();
        return new DemoMySQLDao();
    }
    
}
