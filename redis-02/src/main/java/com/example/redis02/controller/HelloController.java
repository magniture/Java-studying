package com.example.redis02.controller;

import com.example.redis02.anno.AutoIdempotent;
import com.example.redis02.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.controller
 * @Author: zhangq
 * @CreateTime: 2022-11-14  17:36
 * @Description: TODO
 */
@RestController
public class HelloController {
    @Autowired
    TokenService tokenService;

    @GetMapping("/getToken")
    public String getToken() {
        return tokenService.createToken();
    }


    @PostMapping("/hello")
    @AutoIdempotent
    public String hello() {
        return "hello";
    }


}
