package org.zq.thread.a_build;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.a_build
 * @Description: 线程的构建
 */
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) { //一直为true，循环
                try {
                    Thread.sleep(10000); //线程等待10s
                    System.out.println("Thread is runnning");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
