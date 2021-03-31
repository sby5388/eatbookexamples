package com.eat.chapter4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eat.R;


public class HandlerCallbackActivity extends AppCompatActivity implements Handler.Callback {

    private static final String TAG = "HandlerCallbackActivity";
    /**
     * TODO 使用Handler#CallBack时 没必要使用Handler的子类
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(this) {
        @Override
        public void handleMessage(Message msg) {
            // Process message
            // TODO: 2019/8/22 由Handler.Callback 拦截并处理，并对消息做了一些简单的处理
            Log.d(TAG, "handleMessage: " + msg.what);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_callback);
    }

    /**
     * Callback.handleMessage should return true if the message is handled
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            case 1:
                // TODO: 2019/8/22 返回true时，handleMessage不再处理;
                //  返回false handleMessage 继续处理
                msg.what = 11;
                return true;
            default:
                msg.what = 22;
                return false;
        }
    }

    public void onHandlerCallback(View v) {

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
    }


    /**
     * TODO: 2019/10/16 Handler事件分发的处理顺序
     *
     * @see Handler#dispatchMessage(Message)
     */
    private void temp() {
        // TODO: 2019/10/16 优先处理顺序
        //1.如果Message.callback !=null -->Message.callback.run()，终止
        //2.如果Handler.callBack!=null  -->Handler.callback.handlerMassage(message),返回true时，不再进行3
        //3.如果1跟2都不满足，则由Handler.handleMessage进行处理
        // TODO: 2019/10/16 当然也可以修改 dispatchMessage 进行自定义分发
    }
}
