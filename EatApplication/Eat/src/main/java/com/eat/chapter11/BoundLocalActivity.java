package com.eat.chapter11;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;


public class BoundLocalActivity extends AppCompatActivity {

    private LocalServiceConnection mLocalServiceConnection = new LocalServiceConnection();
    private BoundLocalService mBoundLocalService;
    private boolean mIsBound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(BoundLocalActivity.this, BoundLocalService.class),
                mLocalServiceConnection,
                Service.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            try {
                unbindService(mLocalServiceConnection);
                mIsBound = false;
            } catch (IllegalArgumentException e) {
                // No bound service
            }
        }
    }

    private class LocalServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBoundLocalService = ((BoundLocalService.ServiceBinder) iBinder).getService();

            // At this point clients can invoke methods in the Service,
            // i.e. publishedMethod1 and publishedMethod2.
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBoundLocalService = null;
        }
    }
}
