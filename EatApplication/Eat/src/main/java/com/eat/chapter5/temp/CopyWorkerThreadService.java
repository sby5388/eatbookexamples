package com.eat.chapter5.temp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Administrator  on 2019/10/23.
 */
public class CopyWorkerThreadService extends Service {


    public static final String TAG = CopyWorkerThreadService.class.getSimpleName();

    private WorkerThread mWorkerThread;
    private Messenger mWorkerMessenger;

    public static Intent newIntent(Context context) {
        return new Intent(context, CopyWorkerThreadService.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        synchronized (this) {
            if (mWorkerThread == null) {
                try {
                    // TODO: 2019/10/23 阻塞线程？直到对象mWorkerThread 创建完成？避免对象不存而
                    //  被调用引发的空指针闪退
                    wait();
                } catch (InterruptedException e) {
                    // TODO: 2019/10/23
//                    e.printStackTrace();
                }
            }
        }

        return mWorkerMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        // TODO: 2019/10/23
        mWorkerThread = new WorkerThread();
        mWorkerThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mWorkerThread.quit();
    }

    private void onWorkerPrepared() {
        Log.d(TAG, "onWorkerPrepared: ");
        mWorkerMessenger = new Messenger(mWorkerThread.mWorkerHandler);
        synchronized (this) {
            // TODO: 2019/10/23 释放对象，通知其他线程
            notifyAll();
        }
    }

    private class WorkerThread extends Thread {

        private Handler mWorkerHandler;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mWorkerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO: 2019/10/23 mew Message coming
                    switch (msg.what) {
                        case 1:
                            try {
                                msg.replyTo.send(Message.obtain(null, msg.what, 0, 0));
                            } catch (RemoteException e) {
                                Log.e(TAG, e.getMessage());
                            }
                            break;
                        case 2:
                            Log.d(TAG, "Message received");
                            break;
                        default:
                            break;
                    }
                }
            };
            onWorkerPrepared();
            Looper.loop();
        }

        private void quit() {
            mWorkerHandler.getLooper().quit();
        }
    }


}
