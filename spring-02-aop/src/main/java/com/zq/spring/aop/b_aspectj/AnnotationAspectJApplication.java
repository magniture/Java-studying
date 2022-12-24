package com.zq.spring.aop.b_aspectj;

import com.zq.spring.aop.b_aspectj.config.AspectJAOPConfiguration;
import com.zq.spring.aop.b_aspectj.service.FinanceService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//基于注解的AOP配置
public class AnnotationAspectJApplication {
    
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                AspectJAOPConfiguration.class);
        FinanceService financeService = ctx.getBean(FinanceService.class);
        financeService.addMoney(123.45);
    }
}
