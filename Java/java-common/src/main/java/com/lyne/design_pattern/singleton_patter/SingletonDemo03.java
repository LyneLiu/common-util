package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-18:50
 */

public class SingletonDemo03 {

    private static Singleton instance;

    private SingletonDemo03 (){}

    /**
     * @return
     */
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo03.class){
                if (instance == null){
                    /* 该语句不是原子操作，JVM中执行了以下操作：
                     * 1、为instance分配内存；
                     * 2、调用Singleton的构造函数初始化成员；
                     * 3、将instance对象执行分配的内存空间。
                     * JVM的即时编译器会将指令重排序优化，可能会造成1-3-2的操作。
                     */
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
