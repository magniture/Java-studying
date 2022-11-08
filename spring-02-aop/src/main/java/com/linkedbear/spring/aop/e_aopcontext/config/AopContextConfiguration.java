package com.linkedbear.spring.aop.e_aopcontext.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.linkedbear.spring.aop.e_aopcontext")
@EnableAspectJAutoProxy(exposeProxy = true) //没有开启一个 exposeProxy 的属性，导致无法暴露出代理对象，从而无法获取
public class AopContextConfiguration {
    
}
