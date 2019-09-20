package com.eat.chapter10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

public class Chapter10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter10);
    }

    public void onFileDownloadActivity(View v) {
        startActivity(new Intent(this, FileDownloadActivity.class));
    }
}
