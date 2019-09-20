package com.eat.chapter4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author Administrator  on 2019/8/22.
 */
@SuppressLint("Registered")
class CopyLooperActivity extends AppCompatActivity {
    private static final int MY_TASK = 100;
    private MyHandlerThread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThread = new MyHandlerThread();
        mThread.start();
        final LinearLayout linearLayout = new LinearLayout(this);
        final Button button = new Button(this);
        button.setText("模拟耗时操作");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThread != null) {
                    final Handler workerHandler = mThread.mWorkerHandler;
                    if (workerHandler == null) {
                        return;
                    }
                    workerHandler.sendEmptyMessage(MY_TASK);
                }
            }
        });
        linearLayout.addView(button);
        setContentView(linearLayout);

    }

    @Override
    protected void onDestroy() {
        if (mThread != null) {
            final Handler handler = mThread.mWorkerHandler;
            if (handler != null) {
                // TODO: 2019/8/22 退出相应的线程
                handler.getLooper().quitSafely();
            }
        }
        super.onDestroy();
    }

    private static class MyHandlerThread extends Thread {
        private Handler mWorkerHandler;

        @Override
        public void run() {
            Looper.prepare();
            mWorkerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO: 2019/8/22
                    switch (msg.what) {
                        case MY_TASK:
                            runLongTask();
                            break;
                        default:
                            break;
                    }
                }

                private void runLongTask() {
                    // TODO: 2019/8/22 模拟一个耗时任务

                }
            };
            Looper.loop();
            super.run();
        }
    }
}
