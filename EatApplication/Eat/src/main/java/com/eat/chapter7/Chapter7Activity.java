package com.eat.chapter7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

public class Chapter7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7);
    }

    public void onThreadRetainActivity(View v) {
        startActivity(new Intent(this, ThreadRetainActivity.class));
    }

    public void onThreadRetainFragment(View v) {
        startActivity(new Intent(this, ThreadRetainWithFragmentActivity.class));
    }

    public void CopyOnThreadRetainActivity(View v) {
        startActivity(new Intent(this, CopyThreadRetainActivity.class));
    }
}
