package com.eat.chapter9;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator  on 2019/11/25.
 */
public class TaskTrackingThreadPoolTest {
    private TaskTrackingThreadPool mSubject;

    private Runnable mRunnable;
    private Throwable mThrowable = new Exception() {
        @Override
        public String getLocalizedMessage() {
            return "一些异常 : " + super.getLocalizedMessage();
        }
    };

    @Before
    public void setUp() throws Exception {
        mSubject = new TaskTrackingThreadPool();
        mRunnable = () -> {
            System.out.println("this is Runnable !");
            System.out.println("mRunnable.thread = " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


    }

    private int getResult() {
        return mSubject.getNbrOfTasks();
    }

    @Test
    public void run() {
//        mSubject.beforeExecute(Thread.currentThread(), mRunnable);
        System.out.println(" before result = " + getResult());
        mSubject.execute(mRunnable);
        System.out.println(" after  result = " + getResult());
//        mSubject.afterExecute(mRunnable, mThrowable);
        mSubject.terminated();
    }
}