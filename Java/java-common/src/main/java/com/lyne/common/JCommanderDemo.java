package com.lyne.common;

/**
 * Created by nn_liu on 2016/10/19.
 */

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * JCommander是一个小巧的解析命令行参数的Java框架。
 */
public class JCommanderDemo {

    @Parameter(names={"--length", "-l"})
    int length;
    @Parameter(names={"--pattern", "-p"})
    int pattern;

    public static void main(String ... args) {
        JCommanderDemo commander = new JCommanderDemo();
        new JCommander(commander, args);
        commander.run();
    }

    public void run() {
        System.out.printf("%d %d", length, pattern);    // 输出结果：512 2
    }
}
