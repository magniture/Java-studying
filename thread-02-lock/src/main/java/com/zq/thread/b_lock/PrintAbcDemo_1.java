package com.zq.thread.b_lock;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.thread.b_lock

 * @Description: 多线程之间通信
 */
public class PrintAbcDemo_1 {

    private int signal = 0;

    public synchronized void printA() {
        while (signal != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal = 1;
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printB() {
        while (signal != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal = 2;
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printC() {
        while (signal != 2) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 0;
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintAbcDemo_1 printAbcDemo_1 = new PrintAbcDemo_1();
        Thread printAThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    printAbcDemo_1.printA();
                }
            }
        });
        Thread printBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_1.printB();
                }
            }
        });
        Thread printCThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_1.printC();
                }
            }
        });
        printAThread.start();
        printBThread.start();
        printCThread.start();
        Thread.yield();
    }

}
