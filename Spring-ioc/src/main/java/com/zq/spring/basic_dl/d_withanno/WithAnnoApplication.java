package com.zq.spring.basic_dl.d_withanno;

import com.zq.spring.basic_dl.d_withanno.anno.Color;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.stream.Stream;

public class WithAnnoApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("basic_dl/quickstart-withanno.xml");
        Map<String, Object> beans = ctx.getBeansWithAnnotation(Color.class);
        beans.forEach((beanName, bean) -> {
            System.out.println(beanName + " : " + bean.toString());
        });
        //black : com.zq.spring.basic_dl.d_withanno.bean.Black@6536e911
        //red : com.zq.spring.basic_dl.d_withanno.bean.Red@520a3426
        // getBeanDefinitionNames 取出当前 IOC 容器中的所有 bean
        String[] beans1 = ctx.getBeanDefinitionNames();
        // 利用jdk8的Stream快速编写打印方法
        Stream.of(beans1).forEach(System.out::println);
        //black
        //red
        //dog
    }

}
