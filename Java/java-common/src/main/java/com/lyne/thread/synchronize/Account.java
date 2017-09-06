package com.lyne.thread.synchronize;

/**
 * Created by nn_liu on 2016/10/27.
 */

/**
 * 添加synchronized进行锁控制，线程都进入等待状态，进程数大的情况下，JVM直接崩了......
 */
public class Account {
    public String name;
    public float amount;

    public Account(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * deposit模拟用户的取钱行为
     * @param amt
     */
    public synchronized void deposit(float amt){
        float tmp = amount;
        tmp += amt;
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            // ignore
        }

        amount = tmp;
    }

    /**
     * withdraw模拟用户的存钱行为
     * @param amt
     */
    public synchronized void withdraw(float amt){
        float tmp = amount;
        tmp -= amt;

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            // ignore
        }

        amount = tmp;
    }

    public float getBalance(){
        return amount;
    }
}
