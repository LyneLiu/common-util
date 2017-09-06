package com.lyne.thread.synchronize;

/**
 * Created by nn_liu on 2016/10/27.
 */
public class SynchronizedDemo {
    private static int NUM_OF_THREAD = 100;
    static Thread[] threads = new Thread[NUM_OF_THREAD];

    public static void main(String[] args) {
        final Account account = new Account("john",1000.0f);
        for (int i = 0; i < NUM_OF_THREAD; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    account.deposit(10.0f);
                    account.withdraw(10.0f);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < NUM_OF_THREAD; i++) {
            try {
                threads[i].join();  // 等待所有线程运行结束
            }catch (InterruptedException e){
                //ignore
            }
        }

        System.out.println("John's amount:"+account.getBalance());
    }
}
