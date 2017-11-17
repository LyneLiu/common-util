package com.lyne.metric;

import com.lyne.Application;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 通过PublicMetrics实现自定义的度量信息
 *
 * @author nn_liu
 * @Created 2017-06-30-18:49
 */
@Component public class CustomMetric implements PublicMetrics {

    /**
     * 通过PublicMetrics 实现pull metric
     * @return
     */
    @Override public Collection<Metric<?>> metrics() {

        /*int num = System.getenv().size();
        Metric<?> metric = new Metric<Integer>("hello.service.custom.metric", num, new Date());
        HashSet<Metric<?>> set = new HashSet<Metric<?>>();
        set.add(metric);
        return set;*/

        List<Metric<?>> metrics = new ArrayList<Metric<?>>();
        metrics.add(
                new Metric<Long>("spring.startup-date", Application.getContext().getStartupDate()));
        metrics.add(new Metric<Integer>("spring.bean.definitions",
                Application.getContext().getBeanDefinitionCount()));
        metrics.add(new Metric<Integer>("spring.beans",
                Application.getContext().getBeanNamesForType(Object.class).length));
        return metrics;
    }

}
