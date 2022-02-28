package com.max.service.impl;

import com.max.dao.AccountMapper;
import com.max.domain.Account;
import com.max.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    /**
     * 调用dao完成查询所有
     * @return
     */
    @Override
    public List<Account> findAll() {
        List<Account> all = accountMapper.findAll();
        return all;
    }
}
