package com.eat.chapter9;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程数量跟踪的线程池
 */
public class TaskTrackingThreadPool extends ThreadPoolExecutor {

    private static final String TAG = "CustomThreadPool";

    private AtomicInteger mTaskCount = new AtomicInteger(0);

    public TaskTrackingThreadPool() {
        super(3, 3, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Log.d(TAG, "beforeExecute - thread = " + t.getName());
        final int andIncrement = mTaskCount.getAndIncrement();
        Log.d(TAG, "beforeExecute: andIncrement = " + andIncrement);
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Log.d(TAG, "afterExecute - thread = " + Thread.currentThread().getName() + "t = " + t);
        final int andDecrement = mTaskCount.getAndDecrement();
        Log.d(TAG, "beforeExecute: andDecrement = " + andDecrement);
    }

    @Override
    public void terminated() {
        super.terminated();
        Log.d(TAG, "terminated - thread = " + Thread.currentThread().getName());
    }

    public int getNbrOfTasks() {
        return mTaskCount.get();
    }

    private static class Log {
        private Log() {
            throw new RuntimeException();
        }

        private static void d(String tag, String message) {
            System.out.println(tag + " -- " + message);
        }
    }
}
