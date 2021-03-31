package com.eat.chapter4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eat.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;


public class LooperActivity extends AppCompatActivity {

    private MyLooperThread mLooperThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        mLooperThread = new MyLooperThread(this);
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

    private void temp(Message message) {
        if (true) {
            // TODO: 2019/10/16 一个消息对象只能被发送一次，不可以重复
            return;
        }
        Handler handler = new Handler();
        handler.sendMessage(message);
        // TODO: 2019/10/16 把message 插入到队列首部
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(0));
        // TODO: 2019/10/16 延迟2秒钟后插入队列
        handler.sendMessageAtTime(message, TimeUnit.SECONDS.toMillis(2));

        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private static class MyLooperThread extends Thread {
        private final WeakReference<LooperActivity> mReference;
        private Handler mHandler;

        MyLooperThread(LooperActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new WorkerHandler(this);
            Looper.loop();
        }

        //run on workerThread，so it will not blocking UiThread
        private void doLongRunningOperation() {
            // Add long running operation here.
            // TODO: 2019/8/22 模拟一个耗时操作，如果需要更改UI的，可以使用一个弱应用来实现回调接口
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
            final LooperActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            Toast.makeText(activity, "5 Millis Pass", Toast.LENGTH_SHORT).show();

        }

        private static class WorkerHandler extends Handler {
            private WeakReference<MyLooperThread> mReference;

            WorkerHandler(MyLooperThread thread) {
                mReference = new WeakReference<>(thread);
            }

            @Override
            public void handleMessage(Message msg) {
                final MyLooperThread thread = mReference.get();
                if (thread == null) {
                    return;
                }
                if (msg.what == 0) {
                    thread.doLongRunningOperation();
                }
            }
        }
    }
}
