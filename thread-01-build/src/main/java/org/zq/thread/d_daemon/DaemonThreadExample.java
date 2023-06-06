package org.zq.thread.d_daemon;

/**
 * 守护线程在生活中可以类比为服务人员。假设你在家中举行一个晚宴，需要有服务人员提供食物和饮料给客人。
 * 主线程可以看作是主人，负责协调和管理晚宴的进行。而守护线程就是服务人员，他们的任务是为客人提供服务。
 * 当主人决定结束晚宴时，无论服务人员是否完成了任务，他们会自动离开，而不会阻碍主人结束晚宴。
 */
public class DaemonThreadExample {
    public static void main(String[] args) {
        // 创建一个守护线程
        Thread daemonThread = new Thread(new DaemonTask());
        daemonThread.setDaemon(true); // 将线程设置为守护线程
        daemonThread.start(); // 启动线程

        // 主线程休眠3秒钟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread exits.");
    }
}

class DaemonTask implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Daemon thread is running.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

