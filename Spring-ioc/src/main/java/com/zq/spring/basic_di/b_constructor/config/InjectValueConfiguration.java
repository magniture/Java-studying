package com.zq.spring.basic_di.b_constructor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.zq.spring.basic_di.b_constructor.bean")
@PropertySource("classpath:basic_di/value/red.properties")
public class InjectValueConfiguration {

}
