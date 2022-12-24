package com.zq.spring.bean.c_instantiate.bean;

public class CarStaticFactory {
    
    public static Car getCar() {
        return new Car();
    }
    
}
