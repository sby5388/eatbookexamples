package com.eat.chapter9.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/11/25.
 */
public class TempCallable {
    private static void dumpLongTimeAction() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
    }

    private void temp0() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };

        Future<String> future;
    }

    public void myFuture() {
        invokeAny();
//        if (true) {
//            return;
//        }

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "come from callable";
            }
        });
        try {
            final long startTime = System.currentTimeMillis();
            final String result = future.get();
            System.out.println("result = " + result);
            final long endTime = System.currentTimeMillis();

            System.out.println("耗时  = " + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) + " 秒 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // TODO: 2019/11/25 invoke:调用
        try {
            final ArrayList<Callable<String>> tasks = new ArrayList<>();
            final List<Future<String>> futures = executorService.invokeAll(tasks);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        temp(executorService);
    }

    private void temp(ExecutorService executorService) {
        final Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("run on submit");
            }
        });

        final Future<String> stringFuture = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                dumpLongTimeAction();
                return "String come from Callable";
            }
        });

        final String result = "result";
        final Future<String> future = executorService.submit(() -> {
            System.out.println("run on myFuture");
        }, result);

        try {
            final Object o = submit.get();
            System.out.println(o);
            final String s = stringFuture.get();
            System.out.println("s = " + s);
            final String s1 = future.get();
            System.out.println("s1 = " + s1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

    }

    private void invokeAny() {

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        // TODO: 2019/11/26 结果是返回第一个完成的Task ，然后丢弃其他的Task
        final List<Callable<String>> callableList = new ArrayList<>();
        callableList.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                return "result from 2s";
            }
        });
        callableList.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                return "result from 1s";
            }
        });
        try {
            final String invokeAny = executorService.invokeAny(callableList);
            System.out.println("invokeAny = " + invokeAny);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
