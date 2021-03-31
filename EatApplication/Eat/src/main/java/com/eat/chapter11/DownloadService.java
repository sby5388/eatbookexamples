package com.eat.chapter11;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadService extends Service {

    private ExecutorService mDownloadExecutor;
    private int mCommandCount;

    public static Intent newIntent(Context context) {
        return new Intent(context, DownloadService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadExecutor = Executors.newFixedThreadPool(4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadExecutor.shutdownNow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
            mCommandCount++;
        }
        if (intent != null) {
            downloadFile(intent.getData());
        }
        return START_REDELIVER_INTENT;
    }

    private void downloadFile(final Uri uri) {
        mDownloadExecutor.submit(new Runnable() {
            @Override
            public void run() {

                // TODO: 2019/9/9 模拟下载
                // Simulate long file download
                SystemClock.sleep(10000);

                synchronized (this) {
                    if (--mCommandCount <= 0) {
                        stopSelf();
                    }
                }
            }
        });
    }
}
