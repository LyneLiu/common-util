package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-19:20
 */

public class SingletonDemo04 {

    private volatile static Singleton instance;

    private SingletonDemo04 (){}

    /**
     * SingletonDemo03中的双重检验锁的非原子性操作问题，可以通过volatile解决：
     * volatile禁止指令重排序优化。
     * @return
     */
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo03.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
