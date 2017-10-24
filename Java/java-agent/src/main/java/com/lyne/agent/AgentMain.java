package com.lyne.agent;

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
 *
 * @author nn_liu
 * @Created 2017-10-24-17:15
 */

public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation instrumentation){
        System.out.println("###### Java agentmain method executed!");
    }

    public static void premain(String agentArgs, Instrumentation instrumentation){
        System.out.println(">>>>>> Java premain method executed!");
    }

}
