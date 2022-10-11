package com.zq.spring.annotation.b_scan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zq.spring.annotation.b_scan")
public class ComponentScanConfiguration {
}
//只写 @Configuration 注解，随后启动 IOC 容器，那它是感知不到有 @Component 存在的，一定会报 NoSuchBeanDefinitionException 。
//为了解决这个问题，咱需要引入一个新的注解：@ComponentScan