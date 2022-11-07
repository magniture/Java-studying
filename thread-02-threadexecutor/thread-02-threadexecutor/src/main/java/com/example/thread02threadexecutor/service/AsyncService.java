package com.example.thread02threadexecutor.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: com.example.thread02threadexecutor.service
 * @Author: zhangq
 * @CreateTime: 2022-11-07  13:57
 * @Description: TODO
 */
@Service
public class AsyncService {

    @Async
    public void testAsync(){
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" async test");
    }
}
