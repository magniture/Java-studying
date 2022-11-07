package org.example.thread.asyn;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example.thread.asyn
 * @Author: zhangq
 * @CreateTime: 2022-11-07  11:18
 * @Description: 简单的线程池
 */
public class ThreadExecutorDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is submitTask");
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is executeTask");
            }
        });
    }
}
// submit 和 execute 在执行时不会阻塞到调用线程自身，都能达到异步的效果。
