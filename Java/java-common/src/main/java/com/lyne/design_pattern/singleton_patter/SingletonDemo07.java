package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-19:28
 */

public class SingletonDemo07 {

    public static void main(String[] args) {

        // 每个枚举实例都是static final类型的
        Singleton singleton = EasySingleton.INSTANCE.getInstance();
    }

}
