package com.example.thread02threadexecutor.producer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: com.example.thread02threadexecutor.producer
 * @Author: zhangq
 * @CreateTime: 2022-11-09  11:26
 * @Description: 消息生产者
 */
public class Producer {

    private ArrayBlockingQueue<String> queue;

    public Producer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public boolean put(String content){
        try {
            queue.put(content);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void take(){
        queue.poll();
    }
}
