package com.eat.chapter9;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ThreadFactory;

/**
 * 低优先级的线程工厂
 * 线程工厂结构
 */
class LowPriorityThreadFactory implements ThreadFactory {

    private static final String TAG = "LowPriorityThreadFact";
    private static int count = 1;

    @Override
    public Thread newThread(@NonNull Runnable r) {
        final Thread t = new Thread(r);
        // TODO: 2019/11/7 设置线程名称(与数量相关)
        t.setName("LowPrio " + count++);
        // TODO: 2019/11/7 设置优先级
        t.setPriority(4);
        // TODO: 2019/11/7 设置线程异常自处理
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.d(TAG, "Thread = " + t.getName() + ", error = " + e.getMessage());
            }
        });
        return t;
    }
}
