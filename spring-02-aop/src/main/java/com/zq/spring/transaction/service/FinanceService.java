package com.zq.spring.transaction.service;

import com.zq.spring.transaction.aspect.Transactional;
import com.zq.spring.transaction.dao.FinanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {
    
    @Autowired
    FinanceDao financeDao;
    
    @Transactional
    public void transfer(Long source, Long target, int money) {
        financeDao.subtractMoney(source, money);
        int i = 1 / 0;
        financeDao.addMoney(target, money);
    }
}
