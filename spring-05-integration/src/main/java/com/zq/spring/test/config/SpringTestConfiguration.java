package com.zq.spring.test.config;

import com.zq.spring.test.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfiguration {

    @Bean
    public Cat cat() {
        return new Cat();
    }
}
