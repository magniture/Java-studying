package org.example.factory;

import org.example.dao.DemoDao;
import org.example.dao.impl.DemoMySQLDao;
import org.example.service.DemoService;
import org.example.service.impl.DemoServiceImpl;

public class BeanFactory {

    public static DemoService getDemoService() {
        return new DemoServiceImpl();
    }

    public static DemoDao getDemoDao() {
//        return new DemoDaoImpl();
        return new DemoMySQLDao();
    }

}
