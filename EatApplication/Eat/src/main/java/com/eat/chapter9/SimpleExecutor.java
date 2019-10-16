package com.eat.chapter9;

import java.util.concurrent.Executor;

// TODO: 2019/9/9 ？模拟
// TODO: 2019/9/21 模拟简单的线程池，其实并没有线程池
public class SimpleExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}
