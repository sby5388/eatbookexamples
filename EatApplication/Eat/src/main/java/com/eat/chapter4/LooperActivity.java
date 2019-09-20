package com.eat.chapter4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;


public class LooperActivity extends AppCompatActivity {

    MyLooperThread mLooperThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        mLooperThread = new MyLooperThread();
        mLooperThread.start();
    }

    public void onClick(View v) {
        if (mLooperThread.mHandler != null) {
            Message msg = mLooperThread.mHandler.obtainMessage(0);
            mLooperThread.mHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 2019/8/22 及时销毁，释放 ，避免内存泄露
        mLooperThread.mHandler.getLooper().quit();
    }

    private static class MyLooperThread extends Thread {

        private Handler mHandler;

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        doLongRunningOperation();
                    }
                }
            };
            Looper.loop();
        }

        private void doLongRunningOperation() {
            // Add long running operation here.
            // TODO: 2019/8/22 模拟一个耗时操作，如果需要更改UI的，可以使用一个弱应用来实现回调接口

        }
    }
}
