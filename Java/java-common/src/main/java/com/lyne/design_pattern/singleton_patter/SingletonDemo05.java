package com.lyne.design_pattern.singleton_patter;

/**
 * @author nn_liu
 * @Created 2017-11-28-19:24
 */

public class SingletonDemo05 {

    private static final Singleton instance = new Singleton();

    private SingletonDemo05 (){}

    /**
     * 单例会在加载类后一开始被初始化，但是如果实例的创建需要依赖参数或者配置文件，这种单例方法变无法使用。
     * @return
     */
    public static Singleton getInstance() {
        return instance;
    }

}
