package com.linkedbear.spring.basic_dl.d_withanno;

import com.linkedbear.spring.basic_dl.d_withanno.anno.Color;
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
//        getBeanDefinitionNames 可以获取容器中所有的bean
//        String[] beans = ctx.getBeanDefinitionNames();
//        Stream.of(beans).forEach(System.out::println);


    }


}
