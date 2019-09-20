package com.eat.chapter5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eat.R;


/**
 * Activity passing messages to remote service via Messenger.
 */
public class MessengerOneWayActivity extends AppCompatActivity {
    public static final String TAG = MessengerOneWayActivity.class.getSimpleName();

    /**
     * mBind
     */
    private boolean mBound = false;
    // TODO: 2019/9/9 Messenger?
    private Messenger mRemoteService = null;

    private ServiceConnection mRemoteConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected: className = "+className.toString());
            mRemoteService = new Messenger(service);
            Log.e(TAG, "onServiceConnected: ", new Exception());
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "onServiceDisconnected: ", new Exception());
            mRemoteService = null;
            mBound = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
    }

    public void onBindClick(View v) {
        // TODO: 2019/8/6 服务类型的Intent必须显式声明，即具体的服务类的类名
//        Intent intent = new Intent("com.eat.chapter5.ACTION_BIND");
        final Intent intent = WorkerThreadService.newIntent(this);
        bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);

    }

    public void onUnbindClick(View v) {
        if (mBound) {
            unbindService(mRemoteConnection);
            mBound = false;
        }

    }

    public void onSendClick(View v) {
        if (mBound) {
            try {
                mRemoteService.send(Message.obtain(null, 2, 0, 0));
            } catch (RemoteException e) {
                // Empty
            }
        }
    }
}