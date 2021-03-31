package com.eat.chapter9;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

// TODO: 2019/9/9 ？模拟
// TODO: 2019/9/21 模拟简单的线程池，其实并没有线程池
// TODO: 2019/10/30 虽然没有效率，但实现了解耦
public class SimpleExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}
