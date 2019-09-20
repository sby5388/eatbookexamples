package com.eat.chapter11;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eat.R;


public class DownloadActivity extends AppCompatActivity {
    public static final String TAG = "DownloadActivity";
    private static final String DOWNLOAD_URL = "http://www.chaozhou.gov.cn/u/cms/www/201711/15101017mga2.jpg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);
    }

    public void onStartDownload(View v) {
        // TODO: 2019/8/5   服务类型的Intent必须是显式声明
//        Intent intent = new Intent("com.eat.ACTION_DOWNLOAD");
        Intent intent = DownloadService.newIntent(this);
        intent.setData(Uri.parse(DOWNLOAD_URL));
        startService(intent);
    }

    public void onStopService(View v) {
        Intent intent = new Intent(this, DownloadService.class);
        stopService(intent);
    }

    public void onRemoteUnbindService(View view) {
        Log.e(TAG, "onRemoteUnbindService: ", new Exception("未实现"));
    }

    public void onRemoteBindService(View view) {
        Log.e(TAG, "onRemoteBindService: ", new Exception("未实现"));
    }
}