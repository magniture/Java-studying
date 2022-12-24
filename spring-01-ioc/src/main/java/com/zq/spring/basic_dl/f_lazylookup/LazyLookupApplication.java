package com.zq.spring.basic_dl.f_lazylookup;

import com.zq.spring.basic_dl.f_lazylookup.bean.Cat;
import com.zq.spring.basic_dl.f_lazylookup.bean.Dog;
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
        Dog dog = dogProvider.getIfAvailable();
        if (dog != null) {
            System.out.println(dogProvider.getObject());

        }
//        System.out.println(dogProvider.getObject());
        
        // jdk8后ObjectProvider接口有了新的扩展：借助Supplier接口返回缺省创建
        Dog defaultDog = dogProvider.getIfAvailable(Dog::new);
        System.out.println(defaultDog);
        // 只有存在Bean时才会执行Consumer接口的方法
        dogProvider.ifAvailable(System.out::println);
        
    }

//依赖查找多种方式：
//getBeansOfType（xx.class）返回所有实现类
//getBeansWithAnnotation（xx.class）返回所有被该注解标注的类
//getBeanDefinitionNames（）返回容器里所有的bean
//getBeanProvider（）延迟查找，返回包装的bean，通过getIfAvailable获取bean
}
