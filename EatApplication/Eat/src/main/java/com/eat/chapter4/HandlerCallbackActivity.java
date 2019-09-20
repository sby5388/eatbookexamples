package com.eat.chapter4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eat.R;


public class HandlerCallbackActivity extends AppCompatActivity implements Handler.Callback {

    private static final String TAG = "HandlerCallbackActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_callback);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                // TODO: 2019/8/22 返回true时，handleMessage不再处理;返回false handleMessage 继续处理
                msg.what = 11;
                return true;
            default:
                msg.what = 22;
                return false;
        }
    }

    public void onHandlerCallback(View v) {
        Handler handler = new Handler(this) {
            @Override
            public void handleMessage(Message msg) {
                // Process message
                // TODO: 2019/8/22 由Handler.Callback 拦截并处理，并对消息做了一些简单的处理
                Log.d(TAG, "handleMessage: " + msg.what);
            }
        };
        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
    }
}
