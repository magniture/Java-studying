package org.example.thread.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example.thread.lock
 * @Author: zhangq
 * @CreateTime: 2022-11-04  13:20
 * @Description: TODO
 */
public class LockInterruptDemo {



    static class LockInterruptThread_1 implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
                //外界可以中断这里面的处理
                lock.lockInterruptibly();
                try {
                    System.out.println("enter");
                    long startTime = System.currentTimeMillis();
                    for (; ; ) {
                        if (System.currentTimeMillis() - startTime >= 5000) {
                            break;
                        }
                    }
                    System.out.println("end");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(lock.isLocked()){
                        lock.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class LockInterruptThread_2 implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("enter");
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if (System.currentTimeMillis() - startTime >= 5000) {
                        break;
                    }
                }
                System.out.println("end");
            }
        }
    }


    static class LockInterruptThread_3 implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("enter");
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if (System.currentTimeMillis() - startTime >= 5000) {
                        break;
                    }
                }
                System.out.println("end");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(lock.isLocked()){
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void testLockInterruptThread_1() throws InterruptedException {
        LockInterruptThread_1 lockInterruptThread_1 = new LockInterruptThread_1();
        //使用ReentrantLock#lockInterruptibly 在没有获取到锁处于等待过程中是可以被中断的。
        Thread t1 = new Thread(lockInterruptThread_1);
        Thread t2 = new Thread(lockInterruptThread_1);

        t1.start();
        t2.start();

        Thread.sleep(200);
        System.out.println("开始触发中断操作");
        t2.interrupt();
        System.out.println("发起了中断操作");
    }

    @Test
    public void testLockInterruptThread_2() throws InterruptedException {
        LockInterruptThread_2 lockInterruptThread_2 = new LockInterruptThread_2();
        //使用synchronized关键字 在没有获取到锁处于等待过程中是无法随意中断的。
        Thread t1 = new Thread(lockInterruptThread_2);
        Thread t2 = new Thread(lockInterruptThread_2);

        t1.start();
        t2.start();

        Thread.sleep(200);
        System.out.println("开始触发中断操作");
        t2.interrupt();
        System.out.println("发起了中断操作");
    }

    @Test
    public void testLockInterruptThread_3() throws InterruptedException {
        LockInterruptThread_3 lockInterruptThread_3 = new LockInterruptThread_3();
        //使用synchronized关键字 在没有获取到锁处于等待过程中是无法随意中断的。
        Thread t1 = new Thread(lockInterruptThread_3);
        Thread t2 = new Thread(lockInterruptThread_3);

        t1.start();
        t2.start();

        Thread.sleep(200);
        System.out.println("开始触发中断操作");
        t2.interrupt();
        System.out.println("发起了中断操作");
    }
}
