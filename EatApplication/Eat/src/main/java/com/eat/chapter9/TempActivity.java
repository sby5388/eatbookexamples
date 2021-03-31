package com.eat.chapter9;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/**
 * @author Administrator  on 2019/10/30.
 */
public abstract class TempActivity extends AppCompatActivity {

    private static class SerialExecutor implements Executor {

        //ArrayDeque（即双端队列）保存所有提交的任务，直到它们被线程处理为止。
        private final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        private Runnable mActive;

        @Override
        public void execute(@NonNull Runnable command) {
            mTasks.offer(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }


        private synchronized void scheduleNext() {
            // TODO: 2019/10/30
            if ((mActive = mTasks.poll()) != null) {
                getExecutor().execute(mActive);
            }
        }


        private Executor getExecutor() {
            // TODO: 2019/10/30
            throw new RuntimeException("Not implements");
        }
    }


}
