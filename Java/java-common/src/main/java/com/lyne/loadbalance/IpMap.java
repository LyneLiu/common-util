package com.lyne.loadbalance;

import java.util.HashMap;

/**
 * Created by nn_liu on 2016/9/21.
 */
public class IpMap {

    // 待路由的Ip列表，Key代表Ip，Value代表该Ip的权重
    public static HashMap<String, Integer> serverWeightMap =
            new HashMap<String, Integer>();

    static
    {
        serverWeightMap.put("192.168.1.100", 1);
        serverWeightMap.put("192.168.1.101", 1);
        // 权重为4
        serverWeightMap.put("192.168.1.102", 4);
        serverWeightMap.put("192.168.1.103", 4);
        serverWeightMap.put("192.168.1.104", 4);
        // 权重为3
        serverWeightMap.put("192.168.1.105", 3);
        serverWeightMap.put("192.168.1.106", 3);
        // 权重为2
        serverWeightMap.put("192.168.1.107", 2);
        serverWeightMap.put("192.168.1.108", 2);
        serverWeightMap.put("192.168.1.109", 2);
        serverWeightMap.put("192.168.1.110", 2);
    }
}
