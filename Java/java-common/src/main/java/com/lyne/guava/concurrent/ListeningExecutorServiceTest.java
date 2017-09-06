package com.lyne.guava.concurrent;

import com.google.common.util.concurrent.*;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.functors.ForClosure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by nn_liu on 2017/6/7.
 */
public class ListeningExecutorServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        Set<ListenableFuture<Integer>> result = new HashSet<>(2);

        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);

        Closure mapClosure = new Closure() {
            @Override
            public void execute(Object input) {
                result.add(service.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(1000);
                        System.out.println("call future 1.");
                        return (Integer) input;
                    }
                }));
            }
        };

        Closure parallelTask = new ForClosure(1, mapClosure);
        CollectionUtils.forAllDo(numberList, parallelTask);

        service.shutdown();

        ListenableFuture allFutures = Futures.allAsList(result);

        final ListenableFuture transform = Futures.transformAsync(allFutures, new AsyncFunction<List<Integer>, Boolean>() {
            @Override
            public ListenableFuture apply(List<Integer> results) throws Exception {
                return Futures.immediateFuture(String.format("success future:%d!", results.size()));
            }
        });

        Futures.addCallback(transform, new FutureCallback<Object>() {

            public void onSuccess(Object result) {
                System.out.println(result.getClass());
                System.out.printf("success with: %s%n", result);
                System.out.println();
            }

            public void onFailure(Throwable thrown) {
                System.out.printf("onFailure: %s%n", thrown.getMessage());
                System.out.println();
            }
        });

        System.out.println(allFutures.get());

    }

}
