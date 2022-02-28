package com.max.service;

import com.max.domain.Account;

import java.util.List;


public interface AccountService {
    /**
     * 调用dao完成查询所有账户信息
     * @return
     */
    List<Account> findAll();
}