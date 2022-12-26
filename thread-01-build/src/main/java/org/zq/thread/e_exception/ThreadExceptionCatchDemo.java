package org.zq.thread.e_exception;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.e_exception

 * @Description: 线程异常捕捉器
 */
public class ThreadExceptionCatchDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is test");
                int i = 10/0;
            }
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            //这里是对Throwable对象进行监控，所以无论是error或者exception都能识别到
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.err.println("thread is "+t.getName());
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
