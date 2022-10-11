package com.zq.spring.basic_di.d_autowired;

import com.zq.spring.basic_di.d_autowired.bean.Cat;
import com.zq.spring.basic_di.d_autowired.bean.Dog;
import com.zq.spring.basic_di.d_autowired.config.AutowiredConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiredApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AutowiredConfiguration.class);

        Cat cat = ctx.getBean(Cat.class);
        System.out.println(cat);

        Dog dog = ctx.getBean(Dog.class);
        System.out.println(dog);
    }
}
// 【面试题】依赖注入的注入方式
//注入方式	被注入成员是否可变	是否依赖IOC框架的API	使用场景
//构造器注入	不可变	否（xml、编程式注入不依赖）	不可变的固定注入
//参数注入	不可变	否（高版本中注解配置类中的 @Bean 方法参数注入可不标注注解）	注解配置类中 @Bean 方法注册 bean
//属性注入	不可变	是（只能通过标注注解来侵入式注入）	通常用于不可变的固定注入
//setter注入	可变	否（xml、编程式注入不依赖）	可选属性的注入
//【面试题】自动注入的注解对比
//注解	注入方式	是否支持@Primary	来源	Bean不存在时处理
//@Autowired	根据类型注入	是	SpringFramework原生注解	可指定required=false来避免注入失败
//@Resource	根据名称注入	是	JSR250规范	容器中不存在指定Bean会抛出异常
//@Inject	根据类型注入	是	JSR330规范 ( 需要导jar包 )	容器中不存在指定Bean会抛出异常