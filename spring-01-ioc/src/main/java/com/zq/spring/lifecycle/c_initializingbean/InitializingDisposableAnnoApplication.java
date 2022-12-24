package com.zq.spring.lifecycle.c_initializingbean;

import com.zq.spring.lifecycle.c_initializingbean.config.InitializingDisposableConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InitializingDisposableAnnoApplication {
//    总结执行顺序才是最关键的：@PostConstruct → InitializingBean → init-method 。
    public static void main(String[] args) throws Exception {
        System.out.println("准备初始化IOC容器。。。");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                InitializingDisposableConfiguration.class);
        System.out.println("IOC容器初始化完成。。。");
        System.out.println();
        System.out.println("准备销毁IOC容器。。。");
        ctx.close();
        System.out.println("IOC容器销毁完成。。。");
    }
}
