package com.lyne;

import com.lyne.common.visitor.MetricCostCache;

/**
 * @author nn_liu
 * @Created 2017-11-16-19:20
 */

public class ASMCodeTest {

    public void test(){
        System.out.println("========start=========");
        MetricCostCache.setStartTime("newFunc", System.nanoTime());

        MetricCostCache.setEndTime("newFunc", System.nanoTime());
        System.out.println(MetricCostCache.getCostTime("newFunc"));
        System.out.println("========end=========");
    }

}
