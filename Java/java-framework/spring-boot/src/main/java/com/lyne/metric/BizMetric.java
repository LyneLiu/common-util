package com.lyne.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;

/**
 * Metric组件，监控应用
 *
 * @author nn_liu
 * @Created 2017-06-30-17:55
 */

@Component public class BizMetric {

    /* spring内置的两个组件：
     * CounterService——累加累减；
     * GaugeService——存放double值。
     */
    @Autowired private CounterService counterService;

    @Autowired private GaugeService gaugeService;

    public void metricCount(String metricName) {
        this.counterService.increment(metricName);
    }

    public void metricGuage(String metricName, Double val) {
        this.gaugeService.submit(metricName, val);
    }

}
