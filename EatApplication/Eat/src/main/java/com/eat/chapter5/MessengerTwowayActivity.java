package com.eat.chapter5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
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
public class MessengerTwoWayActivity extends AppCompatActivity {
    private static final String TAG = "MessengerTwoWayActivity";
    private boolean mBound = false;
    private Messenger mRemoteService = null;

    private ServiceConnection mRemoteConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            mRemoteService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
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
        // TODO: 2019/8/6  服务类型的Intent必须显式声明
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
                Message msg = Message.obtain(null, 1, 0, 0);
                msg.replyTo = new Messenger(new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d(TAG, "Message sent back - msg.what = " + msg.what);
                    }
                });
                mRemoteService.send(msg);
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}