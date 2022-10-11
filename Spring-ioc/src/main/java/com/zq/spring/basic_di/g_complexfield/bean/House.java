package com.zq.spring.basic_di.g_complexfield.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
public class House {

    @Autowired
    private Cat cat;

    @Autowired
    private Dog dog;

    @Bean
    public Dog getDog() {
        return new Dog();
    }

    @Bean
    public Cat getCat() {
        return new Cat();
    }
}
