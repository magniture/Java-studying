package org.zq.thread.b_group;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.b_group

 * @Description: 线程组
 */
public class ThreadGroupDemo {
    public static List<Thread> DbConnThread() {
        ThreadGroup threadGroup = new ThreadGroup("线程组");
        List<Thread> threadList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                }
            }, "db-conn-thread-" + i);

            threadList.add(thread);
        }

        return threadList;
    }

    public static List<Thread> httpReqThread() {
        ThreadGroup httpReqThreadGroup = new ThreadGroup("第三方http请求线程组");
        List<Thread> httpReqThreadList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(httpReqThreadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                }
            }, "http-req-thread-" + i);
            httpReqThreadList.add(t);
        }
        return httpReqThreadList;
    }

    public static void startThread(List<Thread> threadList) {
        for (Thread thread : threadList) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        List<Thread> dbConnThreadList = DbConnThread();
        List<Thread> httpReqThreadList = httpReqThread();
        startThread(dbConnThreadList);
        startThread(httpReqThreadList);
    }
}
