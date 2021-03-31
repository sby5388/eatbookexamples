package com.eat.chapter9;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/**
 * TODO  书 P135 Example9-2
 *
 * @author Administrator  on 2020/5/28.
 * https://www.cnblogs.com/mfrank/p/9600137.html
 */
public class SerialExecutor implements Executor {
    /**
     * TODO
     * {@link ArrayDeque}双端队列，用数组实现
     * {@link java.util.Deque} 双端队列
     * {@link java.util.Queue} 队列
     */
    final ArrayDeque<Runnable> mRunnables = new ArrayDeque<>();
    private Runnable mActive;

    @Override
    public synchronized void execute(@NonNull final Runnable command) {
        mRunnables.offer(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    scheduleNext();
                }
            }
        });
    }

    private synchronized void scheduleNext() {
        if ((mActive = mRunnables.poll()) != null) {
            mActive.run();
        }
    }
}
