package com.zq.spring.basic_di.b_constructor;

import com.zq.spring.basic_di.b_constructor.bean.Black;
import com.zq.spring.basic_di.b_constructor.bean.Red;
import com.zq.spring.basic_di.b_constructor.config.InjectValueConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectValueAnnoApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(InjectValueConfiguration.class);
        Black black = ctx.getBean(Black.class);
        System.out.println("simple value : " + black);

        Red red = ctx.getBean(Red.class);
        System.out.println("properties value : " + red);

    }
}
