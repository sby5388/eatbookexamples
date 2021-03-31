package com.eat.chapter9;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrestartedCores {


    private static final String TAG = "PrestartedCores";

    public void preloadedQueue() {

        BlockingQueue<Runnable> preloadedQueue = new LinkedBlockingQueue<>();
        final String[] alphabet = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta"};
        for (int i = 0; i < alphabet.length; i++) {
            final int j = i;
            preloadedQueue.add(() -> {
//                    Log.d(TAG, alphabet[j]);
                System.out.println(TAG + " , " + alphabet[j]);
            });
        }
        // TODO: 2019/10/30 线程池构造方法中最后一个参数的含义
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                1, TimeUnit.SECONDS, preloadedQueue);
        executor.prestartAllCoreThreads();
    }
}
