package org.example;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-11-03  15:27
 * @Description: 线程优先级  ，运行后发现，优先级高不一定靠谱，和很多因素有关。不能过于依赖
 */
public class ThreadPriorityDemo {
    static class InnerTask implements Runnable {
        private int i;

        public InnerTask(int i) {
            this.i = i;
        }

    public void run() {
            for (int j = 0; j < 10; j++) {
                System.out.println("ThreadName is " + Thread.currentThread().getName()+" "+j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new InnerTask(10),"task-1");
        t1.setPriority(1);
        Thread t2 = new Thread(new InnerTask(2),"task-2");
        //优先级只能作为一个参考数值，而且具体的线程优先级还和操作系统有关
        t2.setPriority(2);
        Thread t3 = new Thread(new InnerTask(3),"task-3");
        t3.setPriority(3);

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(2000);
    }

}
