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
//什么是 SpringFramework ？为什么要用 SpringFramework ？


//答：1、SpringFrameWork是一个开源、松耦合，分层、可配置式的java企业级应用开发框架，同时集成了许多业界优秀的第三方组件，其核心是IoC及aop。
//使用SpringFrameWork本质上是为了简化开发，让开发者可以专注于业务逻辑，相对于EJB来说更加轻量且易用。从业界流行程度来看，Spring同时也是java开发者最流行的框架，市场份额庞大，生态圈丰富，使用Spring也可以减少沟通和试错成本。