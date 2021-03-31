package com.eat.chapter13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

public class Chapter13Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter13);
    }

    public void onExpandableContactListActivity(View v) {
        startActivity(new Intent(this, ExpandableContactListActivity.class));
    }
}
