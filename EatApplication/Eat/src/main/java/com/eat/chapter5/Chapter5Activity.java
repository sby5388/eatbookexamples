package com.eat.chapter5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

public class Chapter5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5);
    }

    public void onMessengerOnewayActivity(View v) {
        startActivity(new Intent(this, MessengerOneWayActivity.class));
    }

    public void onMessengerTwowayActivity(View v) {
        startActivity(new Intent(this, MessengerTwoWayActivity.class));
    }
}
