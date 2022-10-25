package com.linkedbear.spring.event.a_quickstart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QuickstartListenerApplication {
    
    public static void main(String[] args) throws Exception {
        System.out.println("准备初始化IOC容器。。。");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                "com.linkedbear.spring.event.a_quickstart");
        System.out.println("IOC容器初始化完成。。。");
        ctx.close();
        System.out.println("IOC容器关闭。。。");
    }
}
//ApplicationListener 会在容器初始化阶段就准备好，在容器销毁时一起销毁；
//ApplicationListener 也是 IOC 容器中的普通 Bean ；
//IOC 容器中有内置的一些事件供我们监听。