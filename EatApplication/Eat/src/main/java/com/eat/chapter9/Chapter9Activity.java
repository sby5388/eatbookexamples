package com.eat.chapter9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

// TODO: 2019/9/9 第九章:并发
public class Chapter9Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter9);
    }


    public void onInvokeActivity(View v) {
        startActivity(new Intent(this, InvokeActivity.class));
    }

    public void onECSImageDownloaderActivity(View v) {
        startActivity(new Intent(this, ECSImageDownloaderActivity.class));
    }
}
