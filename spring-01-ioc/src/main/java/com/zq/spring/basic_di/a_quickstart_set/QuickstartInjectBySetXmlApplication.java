package com.zq.spring.basic_di.a_quickstart_set;

import com.zq.spring.basic_di.a_quickstart_set.bean.Cat;
import com.zq.spring.basic_di.a_quickstart_set.bean.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuickstartInjectBySetXmlApplication {
    
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("basic_di/inject-set.xml");
        Person person = beanFactory.getBean(Person.class);
        System.out.println(person);
        
        Cat cat = beanFactory.getBean(Cat.class);
        System.out.println(cat);
    }
//    依赖注入就是创建Bean时带属性，通过set
//还是延续 IOC 的思想，如果你需要属性依赖，不要自己去找，交给 IOC 容器，让它帮你找，并给你赋上值。
}
