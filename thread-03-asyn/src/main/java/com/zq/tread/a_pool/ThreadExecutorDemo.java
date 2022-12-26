package com.zq.tread.a_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.tread.a_pool

 * @Description: 线程池
 */
public class ThreadExecutorDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is submitTask");
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is executeTask");
            }
        });
    }
    //submit: 返回future对象	，如果出现异常时，会将异常抛出，可以实现异步
    //execute：如果出现异常，并且没有加 try-catch 块，线程池会将异常给吞掉。可以实现异步
}
