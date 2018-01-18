package com.lyne.design_pattern.singleton_patter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(50000);
        System.out.println(new BigDecimal(new DecimalFormat("0.00").format(bigDecimal.multiply(new BigDecimal(0.01)))).setScale(0, BigDecimal.ROUND_UP));
        System.out.println(bigDecimal.multiply(new BigDecimal(0.01)).round(new MathContext(4, RoundingMode.UP)));
    }

}
