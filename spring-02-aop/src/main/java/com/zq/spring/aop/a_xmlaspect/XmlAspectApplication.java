package com.zq.spring.aop.a_xmlaspect;

import com.zq.spring.aop.a_xmlaspect.service.FinanceService;
import com.zq.spring.aop.a_xmlaspect.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlAspectApplication {
    //基于xml的aspect实现AOP
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("aop/xmlaspect.xml");
        FinanceService financeService = ctx.getBean(FinanceService.class);
        financeService.addMoney(123.45);
        financeService.subtractMoney(543.21);
        financeService.getMoneyById("abc");
    
        OrderService orderService = ctx.getBean(OrderService.class);
        orderService.createOrder();
        orderService.getOrderById("abcde");
    }
}
