package com.zq.spring.annotation.c_importxml.config;

import com.zq.spring.annotation.a_quickstart.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfigConfiguration {

    @Bean
    public Person person() {
        return new Person();
    }

}
