package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-11-03  16:04
 * @Description: 线程池关闭 shutdown shutdownNow
 */
public class ExecutorShutDownDemo {

    public static void testShutDown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    while (true) {
                    }
                }finally {
                    System.out.println("[testShutDown] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdown();
        //线程池停止接收任务，等待已有任务慢慢执行完。 不会释放锁
        //需要等待线程池将剩余任务执行完毕，期间需要等待一段时间。
        System.out.println("[testShutDown] shutdown");
    }

    public static void testShutDownNow() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    while (true) {
                    }
                }finally {
                    System.out.println("[testShutDown] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdownNow();
        //线程池停止接收任务，已有的任务立马停止处理。释放锁
        //可能会导致一些执行到一半的任务给中断了。
        System.out.println("[testShutDown] shutdownNow");
    }

    public static void main(String[] args) throws InterruptedException {
        testShutDown();
        testShutDownNow();
    }
}
