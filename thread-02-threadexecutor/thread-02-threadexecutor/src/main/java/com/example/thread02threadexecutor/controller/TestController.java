package com.example.thread02threadexecutor.controller;

import com.example.thread02threadexecutor.service.AsyncService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: com.example.thread02threadexecutor.controller
 * @Author: zhangq
 * @CreateTime: 2022-11-07  13:58
 * @Description: TODO
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private AsyncService asyncService;
    @Resource
    private ApplicationContext applicationContext;

    @GetMapping(value = "get-task-pool")
    public String getTaskPool(){
        String[] names = applicationContext.getBeanNamesForType(ExecutorService.class);
        for (String name : names) {
            System.out.println(name);
        }
        return "success";
    }

    @GetMapping(value = "/do-test")
    public String doTest(){
        asyncService.testAsync();
        return "success";
    }
}
