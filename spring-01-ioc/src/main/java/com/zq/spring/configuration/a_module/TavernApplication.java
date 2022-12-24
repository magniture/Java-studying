package com.zq.spring.configuration.a_module;

import com.zq.spring.configuration.a_module.config.TavernConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

public class TavernApplication {
    
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TavernConfiguration.class);
        Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("--------------------------");
//        Boss boss = ctx.getBean(Boss.class);
//        System.out.println(boss);
//        Map<String, Bartender> bartenders = ctx.getBeansOfType(Bartender.class);
//        bartenders.forEach((name, bartender) -> System.out.println(bartender));
    }
}
//模块装配
//1.1 什么是模块装配？将每个独立的模块所需要的核心功能组件进行装配
//1.2 模块装配的核心是什么？通过注解，能快速整合激活相对应的模块。
//2 模块装配的方式有哪几种？一共有四种
//  a @Import + Bean类型
//  b @Import + 配置类
//  c @Import +ImportSelector实现类
//  d @Import +ImportBeanDefinitionRegistrar实现类