package com.lyne.design_pattern.producer_consumer_pattern;

/**
 * @author nn_liu
 * @Created 2017-11-21-18:27
 */

abstract class AbstractProducer implements Producer, Runnable {

    public void run(){
        while (true){
            try {
                produce();
            }catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }

}
