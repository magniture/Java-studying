package org.zq.thread.g_suspend_stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.g_suspend_stop

 * @Description: 线程池的关闭 shutdownNow 和 shuwdown 方法
 */
public class ExecutorShutDownDemo {

//    shutdownNow 方法的解释是：线程池拒接收新提交的任务，同时立马关闭线程池，线程池里的任务不再执行。
//
//    shutdown 方法的解释是：线程池拒接收新提交的任务，同时等待线程池里的任务执行完毕后关闭线程池。

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
        System.out.println("[testShutDown] shutdownNow");
    }

    public static void main(String[] args) throws InterruptedException {
        testShutDown();
        testShutDownNow();
    }

    // 可以发现底层源码中都使用了interrupt 线程中断。

    //shutdown：会对处于空闲中搞得worker线程触发interrupt操作, 等待队列的任务会继续执行
    //shutdownnow: 会对所有忙碌和非忙碌的worker触发interrupt操作, 将队列中没有执行的任务放入到一个 List 集合中，并且返回给调用线程。
}
