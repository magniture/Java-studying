package org.example.dao.impl;

import org.example.dao.DemoDao;

import java.util.Arrays;
import java.util.List;

public class DemoDao2Impl implements DemoDao {

    @Override
    public List<String> findAll() {
        return Arrays.asList("aaa", "bbb", "ccc");
    }
}
