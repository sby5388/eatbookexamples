package com.eat.ch11copy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator  on 2019/8/5.
 */
public class Ch11CopyDownloadService extends Service {
    private ExecutorService mExecutorService;


    public static Intent newIntent(Context context) {
        return new Intent(context, Ch11CopyDownloadService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(4);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
