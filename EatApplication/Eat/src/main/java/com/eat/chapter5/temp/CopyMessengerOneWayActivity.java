package com.eat.chapter5.temp;

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

import com.eat.R;

public class CopyMessengerOneWayActivity extends AppCompatActivity {

    private Messenger mRemoteService;

    private boolean mBind = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO: 2019/10/23 绑定回调
            mRemoteService = new Messenger(service);
            mBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO: 2019/10/23 解除绑定时回调
            mRemoteService = null;
            mBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_messager_one_way);
        findViewById(R.id.button_bind).setOnClickListener(v -> bind());
        findViewById(R.id.button_unbind).setOnClickListener(v -> unbind());
        findViewById(R.id.button_send).setOnClickListener(v -> sendMessage());
    }


    private void bind() {
        final Intent intent = CopyWorkerThreadService.newIntent(this);
        // TODO: 2019/10/23 最后一个参数的含义
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbind() {
        if (!mBind) {
            return;
        }
        unbindService(mConnection);
        mBind = false;

    }

    private void sendMessage() {
        if (!mBind) {
            return;
        }
        final Message message = Message.obtain(null, 2, 0, 0);
        try {
            // TODO: 2019/10/23 向Service发送消息
            mRemoteService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void temp(Thread thread) {
        // TODO: 2019/10/23 只能start() 一次
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
