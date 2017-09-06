package com.lyne.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static net.logstash.logback.marker.Markers.append;

/**
 * 通过MetricsRepository记录metric信息
 *
 * @author nn_liu
 * @Created 2017-07-05-13:53
 */

@Service
class MetricExporterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricExporterService.class);
    private final MetricRepository repository;

    @Autowired
    public MetricExporterService(MetricRepository repository) {
        this.repository = repository;
    }

    @Scheduled(initialDelay = 6000, fixedDelay = 6000)
    void exportMetrics() {
        repository.findAll().forEach(this::log);
    }

    private void log(Metric<?> m) {
        LOGGER.info(append("metric name:", m), "Reporting metric {}={}", m.getName(), m.getValue());
        /* reset操作会清除redis中存储的metric数据 */
        repository.reset(m.getName());
    }
}
