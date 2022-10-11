package com.zq.spring.basic_di.a_quickstart_set;

import com.zq.spring.basic_di.a_quickstart_set.bean.Cat;
import com.zq.spring.basic_di.a_quickstart_set.bean.Person;
import com.zq.spring.basic_di.a_quickstart_set.config.QuickstartInjectBySetConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuickstartInjectBySetXmlApplication {
    public static void main(String[] args) {
//        BeanFactory factory = new ClassPathXmlApplicationContext("basic_di/inject-set.xml");
//        Person person = factory.getBean(Person.class);
//        System.out.println(person);
//
//        Cat cat = factory.getBean(Cat.class);
//        System.out.println(cat);
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(QuickstartInjectBySetConfiguration.class);
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);

        Cat cat = beanFactory.getBean(Cat.class);
        System.out.println(cat);
    }
}
//IOC 的两种实现方式是什么？它们的区别和联系是什么？
//作用目标不同
//依赖注入的作用目标通常是类成员
//依赖查找的作用目标可以是方法体内，也可以是方法体外
//实现方式不同
//依赖注入通常借助一个上下文被动的接收
//依赖查找通常主动使用上下文搜索