package com.eat.chapter4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ConsumeAndQuitThreadActivity extends AppCompatActivity {

    public static final String TAG = "ConsumeAndQuitThread";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ConsumeAndQuitThread consumeAndQuitThread = new ConsumeAndQuitThread();
        consumeAndQuitThread.start();
        // TODO: 2019/8/29 一共发送了4次，只有第一次被响应，其他的都有问题
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 2; i++) {
                        SystemClock.sleep(new Random().nextInt(10));
                        // TODO: 2019/7/26 这里会出现consumeAndQuitThread 创建成功还没有start()完成，就调用，NPE
                        consumeAndQuitThread.enqueueData(i);
                    }
                }
            }).start();
        }
    }

    private static class ConsumeAndQuitThread extends Thread implements MessageQueue.IdleHandler {
        //idle :闲置的
        private static final String THREAD_NAME = "ConsumeAndQuitThread";

        private Handler mConsumerHandler;
        private boolean mIsFirstIdle = true;

        ConsumeAndQuitThread() {
            super(THREAD_NAME);
        }

        @Override
        public void run() {
            Looper.prepare();

            mConsumerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // Consume data
                    Log.d(THREAD_NAME, "handleMessage: msg.what = " + msg.what);
                }
            };
            // TODO: 2019/8/22 》》
            Looper.myQueue().addIdleHandler(this);
            Looper.loop();
        }


        @Override
        public boolean queueIdle() {
            // TODO: 2019/8/29 只有第一次能够处理，之后的handler 的状态是dead ：“ending message to a Handler on a dead thread”
            if (mIsFirstIdle) {
                mIsFirstIdle = false;
                return true;
            }
            // TODO: 2019/8/29 终止线程
            //Terminate the thread.
            mConsumerHandler.getLooper().quit();
            return false;
        }

        //enqueue
        public void enqueueData(int i) {
            mConsumerHandler.sendEmptyMessage(i);
        }

    }


}
