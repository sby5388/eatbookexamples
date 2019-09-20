package com.eat.chapter5;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


/**
 * @author Administrator
 */
public class WorkerThreadService extends Service {


    public static Intent newIntent(Context context) {
        return new Intent(context, WorkerThreadService.class);
    }

    private static final String TAG = "WorkerThreadService";
    WorkerThread mWorkerThread;
    Messenger mWorkerMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkerThread = new WorkerThread();
        mWorkerThread.start();
    }

    /**
     * Worker thread has prepared a looper and handler.
     */
    private void onWorkerPrepared() {
        Log.d(TAG, "onWorkerPrepared");
        mWorkerMessenger = new Messenger(mWorkerThread.mWorkerHandler);
        synchronized (this) {
            // TODO: 2019/8/6 ???
            notifyAll();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        synchronized (this) {
            while (mWorkerMessenger == null) {
                try {
                    // TODO: 2019/8/6 ??
                    wait();
                } catch (InterruptedException e) {
                    // Empty
                }
            }
        }
        return mWorkerMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        mWorkerThread.quit();
    }

    private class WorkerThread extends Thread {

        Handler mWorkerHandler;

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            mWorkerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
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

        public void quit() {
            mWorkerHandler.getLooper().quit();
        }
    }
}
