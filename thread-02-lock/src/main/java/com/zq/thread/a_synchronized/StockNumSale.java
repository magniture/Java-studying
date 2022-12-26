package com.zq.thread.a_synchronized;

import java.util.concurrent.CountDownLatch;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.thread.a_synchronized

 * @Description: TODO
 */
public class StockNumSale {

    //车票剩余数目
    private int stockNum;


    public StockNumSale(int stockNum) {
        this.stockNum = stockNum;
    }

    /**
     * 锁定库存
     *
     * @return 是否锁定成功
     */
//    private boolean lockStock(int num) {
//        if(!isStockEnough()){
//            return false;
//        }
//        for(int i=0;i<num;i++){
//            stockNum--;
//        }
//        return true;
//    }
    public boolean lockStock(int num) {
        synchronized(this){
            if (!isStockEnough()) {
                return false;
            }
            for (int i = 0; i < num; i++) {
                stockNum--;
            }
            return true;
        }
    }

    private boolean isStockEnough(){
        return stockNum>0;
    }


    public void printStockNum() {
        if(this.stockNum<0){
            System.out.println("库存不足：" + this.stockNum);
        }
    }

    public static void batchTest(int threadNum, int stockNum) {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(threadNum);
        StockNumSale stockNumSale = new StockNumSale(stockNum);
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待，模拟并发
                        begin.await();
                        stockNumSale.lockStock(100);
                        end.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        try {
            begin.countDown();
            end.await();
            stockNumSale.printStockNum();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            batchTest(200, 1000);
        }
    }

    //synchronized相等一把锁，未抢到得进入一个同步队列，从而保证线程安全
    // 锁得状态：
    //1.无锁状态：没有被多个线程访问时
    //2.偏向锁状态：被多个线程同时访问一个被加锁的对象，会先进入偏向锁状态
    //3.一旦出现了多个线程同时访问同个 monitor 的时候，偏向锁就会进入撤销状态（底层原理，不多说），进入轻量级锁状态
    //4、当多个竞争的线程抢夺该 monitor 的时候，会采用 CAS 的方式，当抢夺次数超过 10 次，
    // 或者当前 CPU 资源占用大于 50% 的时候，该锁就会从轻量级锁的状态上升为了重量级锁。
    //5、轻量级锁是基于jvm层面的，当线程数非常多时，进入到重量级锁的层面的话，具体的抢夺就需要靠操作系统的内核层面去处理了
}
