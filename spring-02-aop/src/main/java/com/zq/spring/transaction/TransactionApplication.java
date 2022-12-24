package com.zq.spring.transaction;

import com.zq.spring.transaction.config.TransactionAspectConfiguration;
import com.zq.spring.transaction.service.FinanceService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionApplication {
    
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                TransactionAspectConfiguration.class);
        FinanceService financeService = ctx.getBean(FinanceService.class);
        financeService.transfer(1L, 2L, 100);
    }
}
