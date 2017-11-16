package com.lyne.common.visitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nn_liu
 * @Created 2017-11-16-19:06
 */

public class MetricCostCache{

    private static Map<String, Long> startTime = new HashMap<>();

    private static Map<String, Long> endTime = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        startTime.put(methodName, time);
    }
    public static void setEndTime(String methodName, long time) {
        endTime.put(methodName, time);
    }
    public static String getCostTime(String methodName) {
        long start = startTime.get(methodName);
        long end = endTime.get(methodName);
        return "method: " + methodName + " main " + Long.valueOf(end - start) + " ns";
    }

}
