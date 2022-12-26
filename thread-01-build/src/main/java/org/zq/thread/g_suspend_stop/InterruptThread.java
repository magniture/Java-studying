package org.zq.thread.g_suspend_stop;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.g_suspend_stop

 * @Description: 线程中断 interrupt
 */
public class InterruptThread {


    static class TestThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.print(i+" ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestThreadWithSync implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 20; i < 30; i++) {
                    System.out.print(i+" ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class TestThreadWithLock implements Runnable {

        ReentrantLock reentrantLock = new ReentrantLock();

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                for (int i = 30; i < 40; i++) {
                    System.out.print(i+" ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }


    static class ForEverThread implements Runnable {

        @Override
        public void run() {
            System.out.println("开始执行");
            while (true){

            }
        }


    }

    static class TestInterruptedStop implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                //如果当前线程被中断，这里需要主动退出
                while (!Thread.currentThread().isInterrupted()) {
                }
                System.out.println("end");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        Thread testThread = new Thread(new TestThread());
//        testThread.start();
//        Thread testThreadWithSync = new Thread(new TestThreadWithSync());
//        testThreadWithSync.start();
//        Thread testThreadWithLock = new Thread(new TestThreadWithLock());
//        testThreadWithLock.start();
//        Thread forEverThread = new Thread(new ForEverThread());
//        forEverThread.start();
        Thread testInterruptedStop = new Thread(new TestInterruptedStop());
        testInterruptedStop.start();

        Thread.sleep(2000);

//        forEverThread.interrupt();
//        testThread.interrupt();
//        testThreadWithSync.interrupt();
//        testThreadWithLock.interrupt();
        testInterruptedStop.interrupt();

    }

    //被打断的线程异常后依然可以运行，所以interript并不是真正的中断
    //如果线程已经标记为了 interrupt 状态，在线程调用了sleep,join,wait,condition.await 方法的时候会抛出 InterruptedException 异常。

}
