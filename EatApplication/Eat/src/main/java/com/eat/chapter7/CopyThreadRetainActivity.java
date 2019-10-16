package com.eat.chapter7;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eat.R;

import java.lang.ref.WeakReference;

public class CopyThreadRetainActivity extends AppCompatActivity {

    private static MyThread sMyThread;

    private static class MyThread extends Thread {
        private WeakReference<CopyThreadRetainActivity> mReference;

        private MyThread(CopyThreadRetainActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        private void attachActivity(CopyThreadRetainActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            final CopyThreadRetainActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            final String myText = getMyText();
            if (mReference != null) {
                activity.setText(myText);
            }
        }


        private String getMyText() {
//            SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
            SystemClock.sleep(5000);
            return "text come from MyThread";
        }
    }

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_thread_retain);
        mTextView = findViewById(R.id.textView_show);
        // TODO: 2019/9/23 获取上次的保存的配置对象
        final Object object = getLastNonConfigurationInstance();
        if (object != null) {
            sMyThread = (MyThread) object;
            sMyThread.attachActivity(this);
        }

        final Button startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMyThread = new MyThread(CopyThreadRetainActivity.this);
                sMyThread.start();
            }
        });

    }


    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // TODO: 2019/9/23 这个方法在什么时候被调用的
        if (sMyThread != null && sMyThread.isAlive()) {
            return sMyThread;
        }
        return null;
    }

    private void setText(final String text) {
        // TODO: 2019/9/23
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(text);
            }
        });
    }
}
