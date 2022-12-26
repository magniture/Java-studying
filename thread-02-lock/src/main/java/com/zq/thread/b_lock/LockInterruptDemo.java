package com.zq.thread.b_lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.thread.b_lock
 * @Description: 非阻塞锁
 */
public class LockInterruptDemo {


    static class LockInterruptThread_1 implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
                //外界可以中断这里面的处理
                //lockInterruptibly 允许在等待时，由其它线程调用等待线程的 Thread.interrupt 方法来中断等待线程的等待
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

//    通过实验结果可以发现，在多线程场景下，使用 lock函数和synchronized 关键字的代码块在尝试获取锁的过程中，
//    如果获取失败了，就会进入等待队列中等待，而此时使用 Thread.interupt 是无法直接中断在等待状态中的线程的。
//
//    但是对于使用 lockInterruptibly 方法的线程来说，是可以在 Thread.interupt 调用之后立马进入到中断状态的。


//    Lock 类中提供了 tryLock() 函数，这个方法支持线程以非阻塞性的方式去获取锁 ，如果获取失败则立马返回结果；
//    Lock 类中提供了 tryLock(long timeout, TimeUnit unit) 函数，这个方法支持锁的超时机制，当获取锁的时候会先进入等待状态，当等待时间达到预期之后才会返回结果。
//    lockInterruptibly 是一个支持线程中断的函数，当线程在执行该函数之后会进入等待状态，在等待的过程中是可以通过 Thread.interupt 函数去进行中断的。
}
