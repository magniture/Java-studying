package org.zq.thread.c_factory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.c_factory

 * @Description: 线程工厂
 */
public class SimpleThreadFactory implements ThreadFactory {

    private final int maxThread;
    private final String threadGroupName;
    private final String threadNamePrefix;

    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger threadSeq = new AtomicInteger(0);

    private final ThreadGroup threadGroup;


    public SimpleThreadFactory(int maxThread, String threadGroupName, String threadNamePrefix) {
        this.maxThread = maxThread;
        this.threadNamePrefix = threadNamePrefix;
        this.threadGroupName = threadGroupName;
        this.threadGroup = new ThreadGroup(threadGroupName);
    }


    @Override
    public Thread newThread(Runnable r) {
        int c = count.incrementAndGet();
        if (c > maxThread) {
            return null;
        }
        Thread t = new Thread(threadGroup, r, threadNamePrefix + threadSeq.getAndIncrement());
        t.setDaemon(false);
        //默认线程优先级
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ThreadFactory threadFactory = new SimpleThreadFactory(10, "test-thread-group", "test-thread-");
        Thread t = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is task");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        countDownLatch.await();
    }
}
