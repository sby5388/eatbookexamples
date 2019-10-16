package com.eat.chapter4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * @author Administrator  on 2019/8/22.
 */
@SuppressLint("Registered")
class CopyLooperActivity extends AppCompatActivity {
    private static final int MY_TASK = 100;
    private MyHandlerThread mThread;

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThread = new MyHandlerThread(this);
        mThread.start();
        final LinearLayout linearLayout = new LinearLayout(this);
        mButton = new Button(this);
        mButton.setEnabled(false);
        mButton.setText("模拟耗时操作");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThread == null) {
                    return;
                }
                mThread.runMyTask();
            }
        });
        linearLayout.addView(mButton);
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

    private static class MyHandler extends Handler {
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
            SystemClock.sleep(3000);

        }
    }


    private static class MyHandlerThread extends Thread {
        private Handler mWorkerHandler;
        private WeakReference<CopyLooperActivity> mReference;

        MyHandlerThread(CopyLooperActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Looper.prepare();
            // TODO: 2019/10/16 最好使用一个静态的内部类+弱引用引用外部类，防止内存泄露
            mWorkerHandler = new MyHandler();
            Looper.loop();
            mWorkerHandler.post(new Runnable() {
                @Override
                public void run() {
                    final CopyLooperActivity activity = mReference.get();
                    if (activity == null) {
                        return;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.mButton.setEnabled(true);
                        }
                    });
                }
            });
            super.run();
        }


        private void runMyTask() {
            mWorkerHandler.sendEmptyMessage(MY_TASK);

        }
    }
}
