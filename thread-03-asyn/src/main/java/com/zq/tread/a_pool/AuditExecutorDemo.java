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

    //corePoolSize：核心线程数
    //maximumPoolSize: 就是线程池能装几个线程(核心线程 + 非核心线程)
    //当workCount（工作线程）
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
}
