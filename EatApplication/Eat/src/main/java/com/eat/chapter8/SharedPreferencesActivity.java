package com.eat.chapter8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.lang.ref.WeakReference;


public class SharedPreferencesActivity extends AppCompatActivity {

    TextView mTextValue;

    /**
     * Show read value in a TextView.
     */
    private Handler mUiHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<SharedPreferencesActivity> mReference;

        private MyHandler(SharedPreferencesActivity activity) {
            this.mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SharedPreferencesActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            if (msg.what == 0) {
                final Integer i = (Integer) msg.obj;
                activity.mTextValue.setText(String.valueOf(i));
            }
        }
    }

    private class SharedPreferenceThread extends HandlerThread {
        private static final String TAG = "SharedPreferenceThread";

        private static final String KEY = "key";
        private SharedPreferences mPrefs;
        private static final int READ = 1;
        private static final int WRITE = 2;

        private Handler mHandler;

        SharedPreferenceThread() {
            super(TAG, Process.THREAD_PRIORITY_BACKGROUND);
            mPrefs = getSharedPreferences("LocalPrefs", MODE_PRIVATE);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            // TODO: 2019/9/9 Handler(getLooper()) 跟Handler() 的差别 ??
            // FIXME: 2019/9/9

            mHandler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case READ:
                            mUiHandler.sendMessage(mUiHandler.obtainMessage(0, mPrefs.getInt(KEY, 0)));
                            break;
                        case WRITE:
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putInt(KEY, (Integer) msg.obj);
                            editor.apply();
                            break;
                        default:
                            break;
                    }
                }
            };
        }

        void read() {
            mHandler.sendEmptyMessage(READ);
        }

        void write(int i) {
            mHandler.sendMessage(Message.obtain(Message.obtain(mHandler, WRITE, i)));
        }
    }

    private int mCount;
    private SharedPreferenceThread mThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        mTextValue = (TextView) findViewById(R.id.text_value);
        mThread = new SharedPreferenceThread();
        mThread.start();
    }

    /**
     * Write dummy value from the UI thread.
     */
    public void onButtonClickWrite(View v) {
        mThread.write(mCount++);
    }

    /**
     * Initiate a read from the UI thread.
     */
    public void onButtonClickRead(View v) {
        mThread.read();
    }

    /**
     * Ensure that the background thread is terminated with the Activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.quit();
    }
}
