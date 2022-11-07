package org.example.thread.asynchronous;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example.thread.asyn
 * @Author: zhangq
 * @CreateTime: 2022-11-07  11:30
 * @Description: 审核场景
 */
public class AuditExecutorDemo {
    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            1,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

//    执行审核操作
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
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    private static void createAuditRecord() {
        System.out.println("插入审核记录");
    }
    public static void main(String[] args) {
        boolean auditStatus = doAudit();
        System.out.println("提交审核记录结果:" + auditStatus);
    }
}
