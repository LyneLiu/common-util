Metric：应用埋点。
1、Spring boot提供的埋点组件，CounterService和GaugeService；
2、通过PublicMetrics实现自定义的度量信息；
3、MetricsRepository export数据。

Note：
### 1和2中的metric信息，通过http节点即可以访问到。如果想持久化metric信息，可以通过第三种方式的MetricsRepository实现。
### Parameter 0 of constructor in com.lyne.metric.MetricExporterService required a bean of type 'org.springframework.boot.actuate.metrics.repository.MetricRepository' that could not be found.
###    	- Bean method 'actuatorMetricRepository' not loaded because @ConditionalOnJava (older than 1.8) found 1.8

参考链接：
https://kielczewski.eu/2015/01/application-metrics-with-spring-boot-actuator/
