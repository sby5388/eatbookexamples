package com.eat.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

// TODO: 2019/9/23 线程保留 ,Activity 销毁时 继续存活
// FIXME: 2019/9/23 并没有出现预期的效果，关闭Activity 界面后在规定时间内返回Activity，并没有显示新的文本
// FIXME: 2020/8/20 是不是因为旋转导致Activity重建的才是会保留线程？？

public class ThreadRetainAppcompatActivity extends AppCompatActivity {

    private static MyThread t;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retain_thread);
        textView = (TextView) findViewById(R.id.text_retain);
        // TODO: 2019/9/9 字面上的意思：获取上一次的配置实例？
        Object retainedObject = getLastNonConfigurationInstance();
        if (retainedObject != null) {
            t = (MyThread) retainedObject;
            t.attach(this);
        }
    }

    /**
     * TODO: 2019/9/9  AppCompatActivity不能使用
     * {@link Activity#onRetainNonConfigurationInstance}
     * 而是要使用
     * {@link AppCompatActivity#onRetainCustomNonConfigurationInstance}
     *
     * @return
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (t != null && t.isAlive()) {
            return t;
        }
        return null;
    }



    //有点意思，Activity销毁后，如果子线程还在运行，就能直接利用上一次的对象
    // TODO: 2019/9/9  AppCompatActivity不能使用

    public void onStartThread(View v) {
        t = new MyThread(this);
        t.start();
    }

    private void setText(final String text) {
        // TODO: 2019/9/9 下一步判断当前的线程是否在UI线程上，如果是直接run,否则使用Handler#post
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }

    private static class MyThread extends Thread {
        // TODO: 2019/9/23 当前的例子会出现内存泄露的情况，需要弱应用 代替强引用
        private ThreadRetainAppcompatActivity mActivity;

        MyThread(ThreadRetainAppcompatActivity activity) {
            mActivity = activity;
        }

        private void attach(ThreadRetainAppcompatActivity activity) {
            mActivity = activity;
        }

        @Override
        public void run() {
            final String text = getTextFromNetwork();
            mActivity.setText(text);
        }

        // Long operation
        private String getTextFromNetwork() {
            //mock long_time_operation
            // Simulate network operation
            SystemClock.sleep(5000);
            return "Text from network";
        }
    }
}