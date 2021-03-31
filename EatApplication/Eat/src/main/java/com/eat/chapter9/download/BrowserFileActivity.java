package com.eat.chapter9.download;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eat.R;

public class BrowserFileActivity extends AppCompatActivity {
    private static final String TAG = BrowserFileActivity.class.getSimpleName();
    // TODO: 2019/11/5 如果传递过来的已经包含了路径信息呢？
    private static final String ACTION_CODE = "actionCode";
    private static final int ACTION_SET_SAVE_PATH = 0;

    public static Intent newIntentSetSavePath(Context context) {
        return newIntent(context, ACTION_SET_SAVE_PATH);
    }

    public static Intent newIntent(Context context, int actionCode) {
        final Intent intent = new Intent(context, BrowserFileActivity.class);
        intent.putExtra(ACTION_CODE, actionCode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_file);
        final int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onCreate: 缺乏外部存储读取权限-->关闭");
            finish();
        }


    }


}
