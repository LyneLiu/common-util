package com.lyne.premain;

/**
 * 添加VM启动参数：
 * -javaagent:system\path\java-premain-agent-1.0-SNAPSHOT.jar
 *
 * @author nn_liu
 * @Created 2017-11-02-19:02
 */

public class PremainAgentDemo01 {

    public static void main(String[] args) {

        Test01 one = new Test01();
        Test02 two = new Test02();

        System.out.println("PremainAgent Demo Main:" + one.getClass().getSimpleName()
                + " " + two.getClass().getSimpleName());
    }

}

class Test01{}

class Test02{}
