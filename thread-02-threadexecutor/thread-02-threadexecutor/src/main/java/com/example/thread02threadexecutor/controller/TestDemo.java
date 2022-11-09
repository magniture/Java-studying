package com.example.thread02threadexecutor.controller;

import com.example.thread02threadexecutor.consumer.Consumer;
import com.example.thread02threadexecutor.producer.Producer;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: com.example.thread02threadexecutor.consumer
 * @Author: zhangq
 * @CreateTime: 2022-11-09  11:30
 * @Description: 测试代码
 */
public class TestDemo {
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        consumer.start();
        while (true){
            System.out.println("投递元素");
            producer.put(UUID.randomUUID().toString());
            Thread.sleep(2000);
        }
    }

}
