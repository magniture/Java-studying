package org.example.thread.groupandexcutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-10-31  16:53
 * @Description: 采用了线程组技术之后，对于多线程的管理方面会降低一定的复杂度
 */
public class ThreadGroupDemo {

    public static List<Thread> DbThread(){
        ThreadGroup db = new ThreadGroup("数据库连接线程组");
        List<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < 2 ; i++){
            Thread t = new Thread(db, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名:"+ Thread.currentThread().getName() + ", 所在线程组"+ Thread.currentThread().getThreadGroup().getName());

                }
            },"db-conn-thread-" + i);
            threads.add(t);
        }
    return threads;
    }

    public static List<Thread> httpReqThread(){
        ThreadGroup threadGroup = new ThreadGroup("第三方http请求线程组");
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i <2;i++){
            Thread t = new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名:"+Thread.currentThread().getName() + ", 所在线程组"+Thread.currentThread().getThreadGroup().getName());
                }
            }, "http-req-thread-"+i);
            threads.add(t);


        }
        return threads;
    }

    public static void startThread(List<Thread> threadList) {
        for (Thread thread : threadList) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        List<Thread> dbConnThreadList = DbThread();
        List<Thread> httpReqThreadList = httpReqThread();
        startThread(dbConnThreadList);
        startThread(httpReqThreadList);
    }
}
