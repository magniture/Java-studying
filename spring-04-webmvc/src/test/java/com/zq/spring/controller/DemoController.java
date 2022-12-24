package com.zq.spring.controller;

import com.zq.spring.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    
    @Autowired
    private DemoService demoService;
    
    @GetMapping("/ddd")
    public String ddd() {
        System.out.println(demoService);
        return "ddd";
    }
}
