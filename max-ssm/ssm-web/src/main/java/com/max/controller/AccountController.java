package com.max.controller;

import com.max.domain.Account;
import com.max.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 由于是前后端分离开发，后端先前端响应的数据都是json格式的字符串，所以要使用到@ResponseBody注解
 */
@RestController // @Controller + @ResponseBody 这两个注解的组合
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 调用accounservice完成查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<Account> findAll() {
        List<Account> all = accountService.findAll();
        // 会借助@ResponseBody注解将list集合对象转换为json字符串
        return all;
        // [{"id":1,"name":"tom","money":1000.0},{"id":2,"name":"jerry","money":1000.0}]
    }

}
