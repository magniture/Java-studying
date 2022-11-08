package com.linkedbear.spring.aop.d_order.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0) //同切面的多个通知执行顺序,根据unicode编码顺序（字典表顺序）来的
public class LogAspect {
    
    @Before("execution(* com.linkedbear.spring.aop.d_order.service.UserService.*(..))")
    public void printLog() {
        System.out.println("LogAspect 打印日志 ......");
    }
}
