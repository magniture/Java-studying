package org.example;

/**
 * @BelongsProject: thread-01-threadgroup
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-11-03  15:49
 * @Description: 线程终止-stop
 */
public class StopTransferThreadDemo {

    static class User {
        int id;
        int balance;

        public User(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }

    /**
     * 模拟A给B转账
     *
     *  @param  userA
     *  @param  userB
     *  @param  money
     */
    public static void doTransfer(User userA, User userB, int money) throws InterruptedException {
        subtractUserBalance(userA,money);
        addUserBalance(userB,money);
    }

    private static void subtractUserBalance(User user,int money) throws InterruptedException {
        user.balance = user.balance - money;
        //模拟一些网络损耗
        Thread.sleep(1000);
    }

    private static void addUserBalance(User user,int money) throws InterruptedException {
        user.balance = user.balance + money;
        //模拟一些网络损耗
        Thread.sleep(1000);
    }

    static class TransferThread implements Runnable {

        private User userA;
        private User userB;

        public TransferThread(User userA, User userB) {
            this.userA = userA;
            this.userB = userB;
        }

        @Override
        public void run() {
            try {
                doTransfer(userA, userB, 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showBalance(User userA, User userB) {
        System.out.println("用户A余额：" + userA.getBalance() + ",用户B余额：" + userB.getBalance());
    }

    /**
     * 控制转账过程是否要中断
     */
    public static void transferTest(boolean isNormal) throws InterruptedException {
        User userA = new User(1001, 100);
        User userB = new User(1001, 100);
        Thread t1 = new Thread(new TransferThread(userA, userB));
        t1.start();
        Thread.sleep(200);
        if (isNormal) {
            Thread.sleep(2000);
        } else {
            t1.stop();
        }
        showBalance(userA, userB);
    }

    public static void main(String[] args) throws InterruptedException {
        transferTest(true);
    }
}
