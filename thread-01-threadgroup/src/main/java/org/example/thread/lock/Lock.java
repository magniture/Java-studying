package org.example.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface Lock {

    void lock();

    void lockInterruptibly() throws InterruptedException;

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

    Condition newCondition();
}
