package com.max.dao;

import com.max.domain.Account;

import java.util.List;

public interface AccountMapper {
    /**
     * 查询account表中所有的记录
     * @return
     */
    public List<Account> findAll();
}
