package com.eat.chapter8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.lang.ref.WeakReference;


public class SharedPreferencesActivity extends AppCompatActivity {

    private TextView mTextValue;

    /**
     * Show read value in a TextView.
     */
    private Handler mUiHandler = new UiHandler(this);
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

    private void resetHandlerThread(Handler mUiHandler) {
        mUiHandler.removeCallbacksAndMessages(null);
    }

    private void stopHandlerThread(HandlerThread handlerThread) {
        handlerThread.quit();
        handlerThread.interrupt();
    }

    private void temp(Handler handler) {
        // TODO: 2019/10/30 把Runnable查到队列的首部
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private static class UiHandler extends Handler {
        private static final int SHOW_COUNT = 10;
        private final WeakReference<SharedPreferencesActivity> mReference;

        private UiHandler(SharedPreferencesActivity activity) {
            this.mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SharedPreferencesActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            if (msg.what == SHOW_COUNT) {
                final Integer i = (Integer) msg.obj;
                activity.mTextValue.setText(String.valueOf(i));
            }
        }
    }

    private class SharedPreferenceThread extends HandlerThread {
        private static final String TAG = "SharedPreferenceThread";

        private static final String KEY = "key";
        private static final int READ = 1;
        private static final int WRITE = 2;
        private SharedPreferences mPrefs;
        private Handler mHandler;

        SharedPreferenceThread() {
            // TODO: 2019/10/30 线程优先级，1最小，10最大，如果不指定优先级，那么与创建新线程的优先级保持不变
            super(TAG, Process.THREAD_PRIORITY_BACKGROUND);
            mPrefs = getSharedPreferences("LocalPrefs", MODE_PRIVATE);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            // TODO: 2019/9/9 Handler(getLooper()) 跟Handler() 的差别 ??
            // FIXME: 2019/9/9

            final Looper looper = getLooper();
            final String name = looper.getThread().getName();
            // TODO: 2019/10/28 线程名字来自构造方法
            Log.d(TAG, "onLooperPrepared: threadName = " + name);
            mHandler = new Handler(looper) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case READ:
                            mUiHandler.sendMessage(mUiHandler.obtainMessage(UiHandler.SHOW_COUNT, mPrefs.getInt(KEY, 0)));
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
}
