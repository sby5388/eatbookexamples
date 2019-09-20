package com.eat.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eat.R;

import java.util.Random;


public class HandlerExampleActivity extends AppCompatActivity {

    private final static int SHOW_PROGRESS_BAR = 1;
    private final static int HIDE_PROGRESS_BAR = 0;
    private BackgroundThread mBackgroundThread;

    private TextView mText;
    private Button mButton;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);

        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();

        mText = (TextView) findViewById(R.id.text);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/9/9 有可能调用时，mBackgroundThread 还没有能够真正进入可运行的状态
                mBackgroundThread.doWork();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundThread.exit();
    }

    @SuppressWarnings("HandlerLeak")
    private final Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_PROGRESS_BAR:
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case HIDE_PROGRESS_BAR:
                    mText.setText(String.valueOf(msg.arg1));
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    private class BackgroundThread extends Thread {

        private Handler mBackgroundHandler;

        @Override
        public void run() {
            Looper.prepare();
            mBackgroundHandler = new Handler();
            Looper.loop();
        }

        /**
         * 模拟耗时操作：其中ThreadHandler
         */
        public void doWork() {
            mBackgroundHandler.post(new Runnable() {
                @Override
                public void run() {
                    Message uiMsg = mUiHandler.obtainMessage(SHOW_PROGRESS_BAR, 0,
                            0, null);
                    mUiHandler.sendMessage(uiMsg);

                    Random r = new Random();
                    int randomInt = r.nextInt(5000);
                    SystemClock.sleep(randomInt);

                    uiMsg = mUiHandler.obtainMessage(HIDE_PROGRESS_BAR, randomInt,
                            0, null);
                    mUiHandler.sendMessage(uiMsg);
                }
            });
        }

        public void exit() {
            mBackgroundHandler.getLooper().quit();
        }
    }
}
