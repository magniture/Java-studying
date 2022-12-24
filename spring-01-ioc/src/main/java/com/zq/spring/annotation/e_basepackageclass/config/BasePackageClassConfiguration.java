package com.zq.spring.annotation.e_basepackageclass.config;

import com.zq.spring.annotation.e_basepackageclass.bean.DemoService;
import com.zq.spring.annotation.e_basepackageclass.component.DemoComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {DemoService.class, DemoComponent.class})
public class BasePackageClassConfiguration {
    
}
