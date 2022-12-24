package com.zq.spring.definition.a_quickstart.bean;

import org.springframework.stereotype.Component;

@Component
public class Person {
    
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
