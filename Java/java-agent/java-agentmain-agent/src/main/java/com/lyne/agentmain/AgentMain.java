package com.lyne.agentmain;

import com.lyne.common.instrument.CustomClassTransformer;

import java.lang.instrument.Instrumentation;

/**
 * @author nn_liu
 * @Created 2017-11-13-18:24
 */

public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation instrumentation){
        instrumentation = instrumentation;
        instrumentation.addTransformer(new CustomClassTransformer());
    }

}
