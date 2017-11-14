package com.lyne.premain;

import com.lyne.agentmain.AgentMain;
import com.lyne.common.AgentTool;

import java.util.List;

/**
 * @author nn_liu
 * @Created 2017-11-13-18:46
 */

public class AgentmainDemo01 {

    public static void main(String[] args) {

        AgentTool.loadAgent(AgentMain.class.getName());

        Test test = new Test();
        test.newFunc("test");
    }

}


class Test{

    private long getNum(int n, String s, int[] arr) {
        return 0;
    }

    private void greet(String msg) {
        System.out.println(msg);
    }

    public void newFunc(String str) {

        System.out.println(str);
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println(i);
            }
        }
    }
}
