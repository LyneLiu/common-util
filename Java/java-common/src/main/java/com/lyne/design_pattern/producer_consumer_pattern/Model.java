package com.lyne.design_pattern.producer_consumer_pattern;

/**
 * @author nn_liu
 * @Created 2017-11-21-18:31
 */
public interface Model {

    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();

}
