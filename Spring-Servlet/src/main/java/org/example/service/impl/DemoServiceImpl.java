package org.example.service.impl;

import org.example.dao.DemoDao;
import org.example.dao.impl.DemoDaoImpl;
import org.example.service.DemoService;

import java.util.Arrays;
import java.util.List;

public class DemoServiceImpl implements DemoService {

    DemoDao demoDao = new DemoDaoImpl();

    @Override
    public List<String> findAll() {
        return Arrays.asList("oracle", "oracle", "oracle");
    }
}
