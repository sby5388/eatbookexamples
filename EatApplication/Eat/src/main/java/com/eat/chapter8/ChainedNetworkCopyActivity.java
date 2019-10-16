package com.eat.chapter8;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;


public class ChainedNetworkCopyActivity extends AppCompatActivity {
    private static final String DIALOG_LOAD = "DIALOG_LOAD";
    private static final String TAG = "CopyActivity";

    private static final int DIALOG_LOADING = 0;

    private static final int SHOW_LOADING = 1;
    private static final int DISMISS_LOADING = 2;

    private MyFragment mFragment;

    public static class MyFragment extends DialogFragment {
        @Override
        public Dialog getDialog() {
            // TODO: 2019/9/21 并不是实现这里
            return null;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO: 2019/9/21 而是实现这里
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Loading...");
            return builder.create();
        }
    }

    private Handler mUiHandler = new DialogHandler(this);

    private static class DialogHandler extends Handler {
        private final WeakReference<ChainedNetworkCopyActivity> mReference;

        DialogHandler(ChainedNetworkCopyActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ChainedNetworkCopyActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            final MyFragment fragment = activity.mFragment;
            switch (msg.what) {
                case SHOW_LOADING:
                    fragment.show(activity.getSupportFragmentManager(), DIALOG_LOAD);
                    break;
                case DISMISS_LOADING:
                    if (fragment != null) {
                        fragment.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private static class NetworkHandlerThread extends HandlerThread {
        private static final int STATE_A = 1;
        private static final int STATE_B = 2;
        private Handler mWorkHandler;
        private final WeakReference<ChainedNetworkCopyActivity> mWeakReference;

        NetworkHandlerThread(ChainedNetworkCopyActivity activity) {
            super("NetworkHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onLooperPrepared() {
            mWorkHandler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    final ChainedNetworkCopyActivity activity = mWeakReference.get();
                    if (activity == null) {
                        return;
                    }
                    final Handler mUiHandler = activity.mUiHandler;
                    switch (msg.what) {
                        case STATE_A:
                            mUiHandler.sendEmptyMessage(SHOW_LOADING);
                            final String result = networkOperation1();
                            if (result != null) {
                                sendMessage(obtainMessage(STATE_B, result));
                            } else {
                                mUiHandler.sendEmptyMessage(DISMISS_LOADING);
                            }
                            break;
                        case STATE_B:
                            networkOperation2((String) msg.obj);
                            mUiHandler.sendEmptyMessage(DISMISS_LOADING);
                            break;
                    }
                }
            };
            // TODO: 2019/10/16 为了避免mWorkerHandler未创建完成时，
            //  主线程就对该实例进行操作，可以把第一个需要操作的方法F置于这里，
            //  Handler创建后自动运行该方法F
            fetchDataFromNetwork();
        }

        /**
         * 模拟获取耗时
         *
         * @return
         */
        @NonNull
        private String networkOperation1() {
            SystemClock.sleep(2000); // Dummy
            return "A string";
        }

        /**
         * 模拟发送耗时
         *
         * @param data
         */
        private void networkOperation2(String data) {
            // Pass data to network, e.g. with HttpPost.
            SystemClock.sleep(2000); // Dummy
        }

        /**
         * Publicly exposed network operation
         * 公开暴露网络操作？
         */
        public void fetchDataFromNetwork() {
            mWorkHandler.sendEmptyMessage(STATE_A);
        }
    }

    private NetworkHandlerThread mThread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = new MyFragment();
        mThread = new NetworkHandlerThread(this);
        mThread.start();
    }

    /**
     * Ensure that the background thread is terminated with the Activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.quit();
    }

    private void temp() {
    }
}