package com.lyne.thread;

/**
 * Created by nn_liu on 2017/1/24.
 */
public class CustomeThread implements Runnable {

    @Override
    public void run() {
        while (true){
            processSomething();
        }
    }

    private void processSomething() {
        try{
            System.out.println("Processing deamon thread!");
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
