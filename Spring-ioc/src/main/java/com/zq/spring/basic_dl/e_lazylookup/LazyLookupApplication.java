package com.zq.spring.basic_dl.e_lazylookup;

import com.zq.spring.basic_dl.e_lazylookup.bean.Cat;
import com.zq.spring.basic_dl.e_lazylookup.bean.Dog;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LazyLookupApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("basic_dl/quickstart-lazylookup.xml");
        Cat cat = ctx.getBean(Cat.class);
        System.out.println(cat);
        // 下面的代码会报Bean没有定义 NoSuchBeanDefinitionException
        // Dog dog = ctx.getBean(Dog.class);
        // System.out.println(dog);

        // 这一行代码不会报错
        ObjectProvider<Dog> dogProvider = ctx.getBeanProvider(Dog.class);
        // 只有当执行getObject方法时才会检查是否存在，即实现了延迟查找
        System.out.println(dogProvider.getObject());  //报错

        // jdk8后ObjectProvider接口有了新的扩展：借助Supplier接口返回缺省创建
        Dog defaultDog = dogProvider.getIfAvailable(Dog::new);
        System.out.println(defaultDog); //不报错，找不到 Bean 时返回 null 而不抛出异常
        // 只有存在Bean时才会执行Consumer接口的方法
        dogProvider.ifAvailable(System.out::println);

    }
}
//如何对比 BeanFactory 与 ApplicationContext ？ApplicationContext 都在 BeanFactory 的基础上扩展了哪些特性？
//1、ApplicationContext是BeanFactory的子类，功能更为强大。
//2、除了name和type，spring还可以通过annotation来找到相应的类。
//3、依赖查找实际工作中用的很少，基本都是注入。

//依赖查找多种方式：
//getBeansOfType（xx.class）返回所有实现类
//getBeansWithAnnotation（xx.class）返回所有被该注解标注的类
//getBeanDefinitionNames（）返回容器里所有的bean
//getBeanProvider（）延迟查找，返回包装的bean，通过getIfAvailable获取bean