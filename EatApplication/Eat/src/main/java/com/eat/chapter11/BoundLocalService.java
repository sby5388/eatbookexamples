package com.eat.chapter11;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class BoundLocalService extends Service {

    private final ServiceBinder mBinder = new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Methods published to clients.
    public void publishedMethod1() { /* TO IMPLEMENT */ }

    public void publishedMethod2() { /* TO IMPLEMENT */ }

    public class ServiceBinder extends Binder {
        public BoundLocalService getService() {
            return BoundLocalService.this;
        }
    }
}
