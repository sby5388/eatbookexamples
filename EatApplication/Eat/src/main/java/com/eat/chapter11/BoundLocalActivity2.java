package com.eat.chapter11;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.lang.ref.WeakReference;


public class BoundLocalActivity2 extends AppCompatActivity {

    private TextView tvStatus;

    private ServiceConnection mLocalServiceConnection = new LocalServiceConnection();
    private boolean mIsBound;
    private BoundLocalService2 mBoundLocalService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_local_service_sync_client);
        tvStatus = (TextView) findViewById(R.id.text_status);

        bindService(new Intent(BoundLocalActivity2.this, BoundLocalService2.class), mLocalServiceConnection, Service.BIND_AUTO_CREATE);
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

    public void onClickExecuteOnClientUIThread(View v) {
        if (mBoundLocalService != null) {
            mBoundLocalService.doLongAsyncOperation(new ServiceListener(this));
        }
    }

    private static class ServiceListener implements BoundLocalService2.OperationListener {

        private WeakReference<BoundLocalActivity2> mWeakActivity;

        ServiceListener(BoundLocalActivity2 activity) {
            this.mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void onOperationDone(final int someResult) {
            final BoundLocalActivity2 localReferenceActivity = mWeakActivity.get();
            if (localReferenceActivity != null) {
                // TODO: 2019/9/10 运行在主线程上
                localReferenceActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        localReferenceActivity.tvStatus.setText(String.valueOf(someResult));
                    }
                });
            }
        }
    }

    private class LocalServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBoundLocalService = ((BoundLocalService2.ServiceBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBoundLocalService = null;
        }
    }
}