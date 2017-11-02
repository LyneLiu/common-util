package com.lyne.premain;

import com.lyne.premain.instrument.SimpleClassTransformer;

import java.lang.instrument.Instrumentation;

/**
 * @author nn_liu
 * @Created 2017-11-02-18:45
 */

public class PremainAgent {

    /**
     * JVM 首先会尝试invoke静态方法：
     * public static void premain(String agentArgs, Instrumentation inst);
     * 如果上边的方法不存在，则尝试获取另一个静态方法：
     * public static void premain(String agentArgs);
     *
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation){
        System.out.println(">>>>>> Java premain method executed!");
        instrumentation.addTransformer(new SimpleClassTransformer());
    }
}
