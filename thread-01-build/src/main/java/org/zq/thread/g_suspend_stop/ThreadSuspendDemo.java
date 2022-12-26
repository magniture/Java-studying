package org.zq.thread.g_suspend_stop;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.g_suspend

 * @Description: 暂停线程 Suspend已被弃用
 */
public class ThreadSuspendDemo {

    /**
     * 暂停线程
     */
    static class SuspendThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.print(i+" ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread suspendThread = new Thread(new SuspendThread());
        suspendThread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.print("线程暂停");
        //暂停线程
        suspendThread.suspend();
        TimeUnit.SECONDS.sleep(2);
        //从之前暂停的位置恢复继续执行
        suspendThread.resume();
        System.out.print(" 线程恢复");

        //这两个方法已被弃用，因为暂停线程时，一直持有锁，未主动释放过，导致其他想获取锁的一直处于等待状态
    }
}
