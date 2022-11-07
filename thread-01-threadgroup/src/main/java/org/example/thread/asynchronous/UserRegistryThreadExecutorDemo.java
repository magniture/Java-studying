package org.example.thread.asynchronous;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example.thread.asyn
 * @Author: zhangq
 * @CreateTime: 2022-11-07  11:22
 * @Description: 用户注册场景
 */
public class UserRegistryThreadExecutorDemo {

    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            1,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));


    /**
     * 用户注册
     */
    public static void userRegistry() {
        //插入用户信息
        addUserInfo();
        //异步发送消息通知
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sendNotifyMsg();
            }
        });
        System.out.println("用户注册操作流程结束");
    }

    /**
     * 模拟信息插入动作
     */
    private static void addUserInfo() {
        System.out.println("插入用户信息");
    }

    /**
     * 发送通知信息
     */
    private static void sendNotifyMsg() {
        System.out.println("发送消息通知开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送消息通知结束");
    }


    public static void main(String[] args) {
        userRegistry();
    }
}
