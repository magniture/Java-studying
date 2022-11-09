package com.example.thread02threadexecutor.consumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: com.example.thread02threadexecutor.consumer
 * @Author: zhangq
 * @CreateTime: 2022-11-09  11:27
 * @Description: 消息消费者
 */
public class Consumer {

    private ArrayBlockingQueue<String> queue;

    public Consumer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void start() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        String content = queue.take();
                        System.out.println("消费数据：" + content);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
