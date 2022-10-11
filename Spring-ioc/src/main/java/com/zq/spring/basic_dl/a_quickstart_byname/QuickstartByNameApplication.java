package com.zq.spring.basic_dl.a_quickstart_byname;

import com.zq.spring.basic_dl.a_quickstart_byname.bean.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
*@Description xml配置文件，把Person注入到容器，通过上下文获取，通过BeanFactory找出并实例化
*@Return 
*@Author zq
*@Date Created in 2022/10/11 09:58
**/
public class QuickstartByNameApplication {

    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("basic_dl/quickstart-byname.xml");
        Person person = (Person) factory.getBean("person");
        System.out.println(person);
    }
}
