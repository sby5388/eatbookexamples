package com.eat.chapter7;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;

public class ThreadFragment extends Fragment {
    public static final String TAG = ThreadFragment.class.getSimpleName();

    public interface Callback {
        void onSetText(String string);
    }

    private MyThread t;
    private Callback mCallback;


    private class MyThread extends Thread {

        @Override
        public void run() {
            final String text = getTextFromNetwork();
            // TODO: 2019/9/9 应该抽象出一个 setText（String）的接口，对抽象编程，不对实现编程
            mCallback.onSetText(text);
        }

        // Long operation
        private String getTextFromNetwork() {
            // Simulate network operation
            SystemClock.sleep(5000);
            return "Text from network";
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019/9/9 暂存对象
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        if (context instanceof Callback) {
            Log.d(TAG, "onAttach: ");
            mCallback = (Callback) context;
        } else {
            throw new RuntimeException("Context not instanceof ThreadFragment.Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void execute() {
        t = new MyThread();
        t.start();
    }
}
