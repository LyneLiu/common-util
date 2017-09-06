package com.lyne.loadbalance;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by nn_liu on 2016/9/21.
 */


/**
 * 负载均衡算法总结
 */
public class RoundRobin implements Runnable{

    public static final Logger log = Logger.getLogger(RoundRobin.class);

    private static Integer pos = 0;

    public static String getServer() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);

        // 将所有Ip地址整合至List中，轮询情况下访问效率相对较高
        Set<String> keySet = serverMap.keySet();
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String server = null;

        /*通过轮询算法*/
        synchronized (pos) {
            if (pos >= keySet.size())
                pos = 0;
            server = keyList.get(pos);
            pos++;
        }

        return server;
    }

    public void run() {
        log.debug(getServer());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new RoundRobin().run();
        }
    }

}
