package com.zq.architecture.f_pointslog.service;

import java.util.List;

public interface DemoService {
    List<String> findAll();
    
    int add(String userId, int points);
    int subtract(String userId, int points);
    int multiply(String userId, int points);
    int divide(String userId, int points);
}
