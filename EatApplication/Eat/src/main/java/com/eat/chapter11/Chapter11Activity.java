package com.eat.chapter11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;
import com.eat.chapter10.FileDownloadActivity;

public class Chapter11Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter11);
    }

    public void onBluetoothActivity(View v) {
        startActivity(new Intent(this, BluetoothActivity.class));
    }

    public void onDownloadActivity(View v) {
        startActivity(new Intent(this, DownloadActivity.class));
    }

    public void onBoundLocalActivity(View v) {
        startActivity(new Intent(this, BoundLocalActivity.class));
    }

    public void onBoundLocalActivity2(View v) {
        startActivity(new Intent(this, BoundLocalActivity2.class));
    }
}
