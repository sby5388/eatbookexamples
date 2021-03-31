package com.eat.temp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.eat.R;

import java.util.concurrent.TimeUnit;

public class CountDownTimerActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    /**
     * TODO 从调用start()开始，到计时结束的时间，也就是倒计时的总时间，单位为毫秒
     */
    private long millisInFuture = TimeUnit.SECONDS.toMillis(10);
    /**
     * TODO 倒计时间的隔时间，单位为毫秒，
     */
    private long countDownInterval = TimeUnit.SECONDS.toMillis(1);
    /**
     * 一个用于Android 平台上的倒计时工具
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
        /**
         * 每隔#countDownInterval 调用一次，
         * @param millisUntilFinished 倒计时剩余的时间，单位都是毫秒
         */
        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(getRemainTime(millisUntilFinished));
        }

        /**
         * 完成时回调
         */
        @Override
        public void onFinish() {
            mTextView.setText("完成");
            mButton.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);
        // TODO: 2019/11/7 start#之后是如何运行的，内部的原理是什么，有没有什么提前停止的API
        //  内部也是使用了一个Handler来封装
        mButton = findViewById(R.id.button_start_time);
        mButton.setOnClickListener(v -> {
            mCountDownTimer.start();
            mButton.setEnabled(false);
        });
        mTextView = findViewById(R.id.textView_show_time);
    }

    private String getRemainTime(long millisUntilFinished) {
        return "剩余时间" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + "秒";
    }
}
