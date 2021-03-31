package com.eat.chapter10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;
import com.eat.temp.CountDownTimerActivity;

public class Chapter10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter10);
        findViewById(R.id.button_to_count_down_timer).setOnClickListener(v -> {
            startActivity(new Intent(this, CountDownTimerActivity.class));
        });
    }

    public void onFileDownloadActivity(View v) {
        startActivity(new Intent(this, FileDownloadActivity.class));
    }
}
