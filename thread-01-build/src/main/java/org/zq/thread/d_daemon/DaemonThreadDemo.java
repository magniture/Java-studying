package org.zq.thread.d_daemon;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.d_daemon

 * @Description: 守护线程
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
        //1
        //testThread.setDaemon(true); //可以把这个注释看看
        testThread.start();

//        2
//        Thread.sleep(3000);
//        testThread.setDaemon(true);
        //守护线程具有在JVM退出的时候也自我销毁的特点，而非守护线程不具备这个特点，这也是为什么GC回收线程被设置为守护线程类型的主要原因。
    }
}
