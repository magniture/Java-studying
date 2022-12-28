package com.zq.tread.a_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.tread.a_pool

 * @Description: TODO
 */
public class AuditExecutorDemo {

    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            1,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

//    corePoolSize 核心线程数；
//    maximumPoolSize 最大线程数；
//    keepAliveTime 线程活跃时间；
//    zaResizableCapacityLinkedBlockingQueue 基于 JDK 改造的可伸缩队列；
//    allowCoreThreadTimeOut 允许核心线程数超时后被回收；
//    preStartCoreThread 是否要在一开始就启动 corePoolSize 数量的线程数
//    preStartAllCoreThreads 是否要在一开始就启动 maximumPoolSize 数量的线程数
    //具体线程池动画操作，看awebp图

    /**
     * 执行审核操作
     */
    public static boolean doAudit() {
        createAuditRecord();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("发送审核记录给到A部门负责人");
                    Thread.sleep(1000);
                    System.out.println("发送审核记录给到B部门负责人");
                    Thread.sleep(1000);
                    System.out.println("发送审核记录给到C部门负责人");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    /**
     * 模拟将审核记录写入到数据库中
     */
    private static void createAuditRecord() {
        System.out.println("插入审核记录");
    }

    public static void main(String[] args) {
        boolean auditStatus = doAudit();
        System.out.println("提交审核记录结果:" + auditStatus);
    }


    //线程池没有做优雅关闭，导致部分运行到一半的任务被杀掉；
    //线程池执行submit的时候，内部出了异常，但是没有加入try-catch代码块，导致异常被吞；
    //线程池的队列长度配置过大，导致应用服务出现 oom；
    //线程池的内部希望获取到外部的线程变量，可以使用 TransmittableThreadLocal 来获取；
    //高并发场景下不适合使用线程池接收任务，适合使用 MQ 来替代。
}
