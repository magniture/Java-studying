package com.zq.spring.annotation.a_quickstart;

import com.zq.spring.annotation.a_quickstart.bean.Person;
import com.zq.spring.annotation.a_quickstart.config.QuickstartConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(QuickstartConfiguration.class);
//        Person person = ctx.getBean(Person.class);
        Person person = (Person) ctx.getBean("zq");
        System.out.println(person.getName()+":"+person.getAge());
    }
}
