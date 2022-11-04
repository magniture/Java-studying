package org.example.thread.groupandexcutor;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-11-01  10:34
 * @Description: TODO
 */
public class ThreadExceptionCatchDemo {
    public static  void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is test");
                int i = 10/0;
            }
        });

        //是对线程进行监控，只要是异常就会回调到UncaughtExceptionHandler
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("thread is"+ t.getName());
                e.printStackTrace();
            }
        });
        thread.start();
    }


}
