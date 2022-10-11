package com.zq.spring.basic_di.g_complexfield;

import com.zq.spring.basic_di.g_complexfield.bean.House;
import com.zq.spring.basic_di.g_complexfield.bean.Person2;
import com.zq.spring.basic_di.g_complexfield.config.InjectComplexFieldConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComplexFieldAnnoApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(InjectComplexFieldConfiguration.class);
        House person = ctx.getBean(House.class);
        System.out.println(person.getCat()+":"+person.getDog());
    }
}
