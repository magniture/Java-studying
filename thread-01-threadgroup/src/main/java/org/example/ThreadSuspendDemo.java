package org.example;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-11-03  15:36
 * @Description: 终止线程的手段-suspend
 */
public class ThreadSuspendDemo {

    static class SuspendThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100 ;i++) {
                System.out.println(i+"");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread suspendThread = new Thread(new SuspendThread());
        suspendThread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("线程暂停");

        suspendThread.suspend();
        TimeUnit.SECONDS.sleep(2);
        //从之前暂停的位置恢复继续执行
        suspendThread.resume();
        System.out.print(" 线程恢复");
        TimeUnit.SECONDS.sleep(2);
        System.out.print(" 线程终止");
        suspendThread.stop();
    }
}
