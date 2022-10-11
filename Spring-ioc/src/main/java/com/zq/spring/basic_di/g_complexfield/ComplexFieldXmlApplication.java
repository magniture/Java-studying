package com.zq.spring.basic_di.g_complexfield;

import com.zq.spring.basic_di.g_complexfield.bean.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComplexFieldXmlApplication {

    public static void main(String[] args) throws Exception {
        BeanFactory ctx = new ClassPathXmlApplicationContext("basic_di/complexfield/inject-complexfield.xml");
        Person person = ctx.getBean(Person.class);
        System.out.println(person);
    }
}
