package com.zq.tread.a_pool.test;

import com.zq.tread.a_pool.domain.Account;
import org.junit.Test;

import java.util.concurrent.atomic.*;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: PACKAGE_NAME
 * @Author: zhangq
 * @CreateTime: 2022-12-28  10:24
 * @Description: TODO
 */
public class test {

    @Test
    public void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(atomicInteger.addAndGet(1));
        System.out.println(atomicInteger.getAndAdd(1));
    }

    @Test
    public void testAtomicBoolean(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        atomicBoolean.set(false);
        System.out.println(atomicBoolean.get());
    }

    @Test
    public void testAtomicIntegerArray(){
        int[] value = new int[]{0, 1, 2};
        AtomicIntegerArray ata = new AtomicIntegerArray(value);
        //先指定数组的下标位，在指定需要增加的数值
        ata.addAndGet(0,1);
        System.out.println(ata);
    }

    @Test
    public void testAtomicReference(){
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("idea");
        System.out.println(stringAtomicReference.compareAndSet("idea","idea2"));
        System.out.println(stringAtomicReference.compareAndSet("idea2","idea"));
        System.out.println(stringAtomicReference.compareAndSet("idea","idea2"));
    }


    /**
     * 更新整个对象的类型
     */
    @Test
    public void updateAccount(){
        AtomicReference<Account> accountAtomicReference = new AtomicReference<>();
        Account account = new Account();
        account.setAge(1);
        account.setId(1);
        account.setName("idea");
        accountAtomicReference.set(account);
        Account account2 = new Account();
        account2.setAge(2);
        account2.setId(2);
        account2.setName("idea2");
        accountAtomicReference.compareAndSet(account,account2);
        System.out.println(accountAtomicReference.get().getAge());
        System.out.println(accountAtomicReference.get().getId());
        System.out.println(accountAtomicReference.get().getName());
    }


}
