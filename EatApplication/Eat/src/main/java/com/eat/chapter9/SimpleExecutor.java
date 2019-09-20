package com.eat.chapter9;

import java.util.concurrent.Executor;

// TODO: 2019/9/9 ？
public class SimpleExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}
