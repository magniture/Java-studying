package org.zq.thread.f_priority;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.zq.thread.f_priority

 * @Description: 线程优先级
 */
public class ThreadPriorityDemo {

    static class InnerTask implements Runnable {

        private int i;

        public InnerTask(int i) {
            this.i = i;
        }

        public void run() {
            for(int j=0;j<10;j++){
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

        //理论上，级别越高，优先级越高，但运行后发现并非如此，运行优先级还和操作系统有关。
    }
}
