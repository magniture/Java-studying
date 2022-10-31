package org.example;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-10-31  17:37
 * @Description: 守护线程具有在JVM退出的时候也自我销毁的特点，而非守护线程不具备这个特点，这也是为什么GC回收线程被设置为守护线程类型的主要原因。
 */
public class DaemonThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("jvm exit success!! ")));

        Thread testThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("thread still running ....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //testThread.setDaemon(true);
        testThread.start();
    }
}
