package com.eat.chapter5.book;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/10/25.
 */
public class Temp {
    public static final String TAG = "Temp";

    private final ISynchronous.Stub mSynchronous = new ISynchronous.Stub() {
        // TODO: 2019/10/25 这个并发类的作用？
        private static final int THREAD_COUNT = 1;
        private CountDownLatch mLatch = new CountDownLatch(THREAD_COUNT);

        @Override
        public String getThreadNameFast() throws RemoteException {
            return getThreadName();
        }

        @Override
        public String getThreadNameSlow(long sleep) throws RemoteException {
            SystemClock.sleep(sleep);
            return getThreadName();
        }

        /**
         *{@link #THREAD_COUNT}
         * @return
         * @throws RemoteException
         */
        @Override
        public String getThreadNameBlocking() throws RemoteException {
            try {
                // TODO: 2019/10/25 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
                //  count的值就是构造方法中的 #THREAD_COUNT
                mLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getThreadName();
        }

        @Override
        public String getThreadNameUnlock() throws RemoteException {
            // TODO: 2019/10/25 将count值减1
            mLatch.countDown();
            return getThreadName();
        }

        private String getThreadName() {
            return Thread.currentThread().getName();
        }
    };


    private final IAsynchronous1.Stub mAsynchronous1 = new IAsynchronous1.Stub() {
        @Override
        public void getThreadNameSlow(IAsynchronousCallback callback) throws RemoteException {
            // TODO: 2019/10/25 AIDL语言的回调
            final String name = Thread.currentThread().getName();
            //Simulate a slow call
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
            callback.handleResult(name);
        }
    };


    private final IAsynchronousCallback mAsynchronousCallback = new IAsynchronousCallback() {
        @Override
        public void handleResult(String threadName) throws RemoteException {
            // TODO: 2019/10/25 分别打印调用方的线程和当前的线程
            Log.d(TAG, "handleResult: remoteThreadName = " + threadName);
            Log.d(TAG, "handleResult: currentThreadName = " + Thread.currentThread().getName());
        }

        @Override
        public IBinder asBinder() {
            // TODO: 2019/10/25 why has this function
            return null;
        }
    };


    private void temp(IBinder binder) throws RemoteException {
        final ISynchronous synchronous = ISynchronous.Stub.asInterface(binder);
        final String threadNameFast = synchronous.getThreadNameFast();
        Log.d(TAG, "temp: threadNameFast = " + threadNameFast);
    }

    private void temp(Thread t) {
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

            }
        });
    }

}
