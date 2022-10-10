package org.example.factory;

import org.example.dao.DemoDao;
import org.example.dao.impl.DemoMySQLDao;
import org.example.service.DemoService;
import org.example.service.impl.DemoServiceImpl;

public class BeanFactory {

    public static DemoService getDemoService() {
        try {
            return (DemoService) Class.forName("com.linkedbear.architecture.c_reflect.service.impl.DemoServiceImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DemoService instantiation error, cause: " + e.getMessage());
        }
    }

    public static DemoDao getDemoDao() {
        try {
            return (DemoDao) Class.forName("com.linkedbear.architecture.c_reflect.dao.impl.DemoDaoImpl").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DemoDao instantiation error, cause: " + e.getMessage());
        }
    }

}
