package org.zq.thread.a_build;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 当使用线程池执行任务时，线程池会按照以下流程进行处理：
 *
 * 1. 任务提交：外部程序将任务提交给线程池。
 *
 * 2. 任务接收：线程池接收到任务后，首先会检查当前线程池中的线程数量和状态。如果线程池中的线程数小于核心线程数，且线程池处于运行状态，线程池会创建一个新的线程来执行任务。如果线程池中的线程数已经达到核心线程数，线程池会将任务放入阻塞队列中等待执行。
 *
 * 3. 任务执行：线程池中的线程从阻塞队列中取出任务并执行。执行过程中，线程池会根据需要从阻塞队列中获取新的任务。
 *
 * 4. 队列管理：当阻塞队列已满时，线程池会根据配置的策略进行处理。常见的策略有：直接丢弃任务、抛出异常、阻塞调用者或创建新的线程执行任务。
 *
 * 5. 线程回收：在任务执行完毕后，线程会返回线程池，并等待新的任务。如果线程池中的线程空闲时间超过设定的时间（keepAliveTime），则线程池可能会销毁多余的线程，以减少资源消耗。
 *
 * 6. 关闭线程池：当不再需要线程池时，可以调用线程池的关闭方法。关闭线程池后，线程池将不再接收新的任务，并且会等待正在执行的任务执行完毕。然后，线程池中的线程会被终止。
 *
 * 通过以上流程，线程池能够实现任务的调度、线程的复用和资源的管理，提高系统的性能和资源利用率。在实际应用中，合理配置线程池的参数和选择适当的阻塞队列类型，可以根据具体需求来平衡任务的处理能力和系统资源的消耗。
 *
 */
public class ThreadPoolExample {
    public static void main(String[] args) {
        // 创建一个固定大小为2的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // 提交任务到线程池
        threadPool.execute(new Task("Task 1"));
        threadPool.execute(new Task("Task 2"));
        threadPool.execute(new Task("Task 3"));

        // 关闭线程池
        threadPool.shutdown();
    }
}

class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task " + name + " is executing.");
        // 执行任务的具体逻辑
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task " + name + " is completed.");
    }
}

