package com.lyne.multithread;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author nn_liu
 * @Created 2017-11-03-14:59
 */

public class ExecutorServiceDemo {

    /*
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     */
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    /*
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    private ExecutorService executorService1  = Executors.newSingleThreadExecutor();
    /*
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     */
    private ExecutorService executorService2  = Executors.newFixedThreadPool(10);
    /*
     * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
     */
    private ExecutorService executorService3  = Executors.newScheduledThreadPool(10);

    public void cacheThreadPool(){

        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                }
            });
        }

        cachedThreadPool.shutdown();
    }

    /**
     * Runnable无返回结果。
     */
    public void executeRunnable(){

        executorService1.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });

        executorService1.shutdown();
    }

    public void submitRunnable() {

        Future future = executorService2.submit(new Runnable(){
            public void run() {
                System.out.println("Asynchronous Runnable");
            }
        });

        //如果任务结束执行则返回 null
        try {
            System.out.println("future.get()=" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService2.shutdown();
    }

    /**
     * Callable返回执行结果
     */
    public void submitCallable(){

        Future future = executorService3.submit(new Callable() {
            @Override public Object call() throws Exception {
                System.out.println("Asynchronous Callable");
                return "Callable result";
            }
        });

        try {
            System.out.println("future.get()=" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService3.shutdown();
    }

    /**
     * 执行Callable集合中的所有task，存在执行成功的情况，返回其中执行成功的任意一个结果；
     * 如果都执行失败，则抛出最后一个执行的任意一个异常信息。
     */
    public void invokeAny(){

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        callables.add(new Callable<String>() {
            @Override public String call() throws Exception {
                throw new ExecutionException(new Throwable("run error 01!"));
            }
        });

        /*callables.add(new Callable<String>() {
            @Override public String call() throws Exception {
                throw new ExecutionException(new Throwable("run error 02!"));
            }
        });

        callables.add(new Callable<String>() {
            @Override public String call() throws Exception {
                throw new ExecutionException(new Throwable("run error 03!"));
            }
        });*/

        String result = null;
        try {
            result = executorService1.invokeAny(callables);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("invokeAny result = " + result);

        executorService1.shutdown();

    }

    /**
     * 执行Callable集合中所有的task，并返回每一个task的执行结果，包括执行成功的和抛出异常的。
     */
    public void invokeAll(){

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        /*callables.add(new Callable<String>() {
            @Override public String call() throws Exception {
                throw new ExecutionException(new Throwable("run error 01!"));
            }
        });*/

        List<Future<String>> futures = null;
        try {
            futures = executorService1.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("invokeAll result:");
        if (!futures.isEmpty()) {
            for (Future future : futures) {
                try {
                    System.out.println("task name:" + future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executorService1.shutdown();

    }


    public static void main(String[] args) {
        ExecutorServiceDemo executorServiceDemo = new ExecutorServiceDemo();
        System.out.println("==============cachedThreadPool demo=================");
        //executorServiceDemo.cacheThreadPool();
        System.out.println("==============execute Runnable demo=================");
        //executorServiceDemo.executeRunnable();
        System.out.println("==============submit Runnable demo=================");
        //executorServiceDemo.submitRunnable();
        System.out.println("==============submit Callable demo=================");
        //executorServiceDemo.submitCallable();
        System.out.println("==============invokeAny demo=================");
        //executorServiceDemo.invokeAny();
        System.out.println("==============invokeAll demo=================");
        executorServiceDemo.invokeAll();
    }

}
