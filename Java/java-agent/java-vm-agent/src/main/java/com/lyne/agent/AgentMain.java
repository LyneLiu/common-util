package com.lyne.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 *
 * Java Agent的包结构和普通的jar包结构没有什么区别，值得注意的是MANIFEST.MF文件中多了
 * Premain-class和Agent-class两个配置项。
 *
 * premain-class：顾名思义，即在java程序的main方法执行之前需要执行哪些方法。
 * agent-class：在java虚拟机启动之后，如果再通过attach方式加载java agent，就会调用这个配置的类中的名为agentmain方法。
 *
 * 参考链接：
 * http://www.jianshu.com/p/ae6f28fff289
 * http://fengfu.io/2016/04/24/Java-Instrumentation%E7%A0%94%E7%A9%B6-1/
 *
 * @author nn_liu
 * @Created 2017-10-24-17:15
 */

public class AgentMain {

    static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private static Instrumentation instrumentation;

    public static void agentmain(String agentArgs, Instrumentation instrumentation){
        logger.info("agentmain method invoked with args: {} and inst: {}", agentArgs, instrumentation);
        instrumentation = instrumentation;
        instrumentation.addTransformer(new ClassFileTransformer());
    }

    public static void premain(String agentArgs, Instrumentation instrumentation){
        System.out.println(">>>>>> Java premain method executed!");
    }

}
