package com.eat.chapter8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;

public class Chapter8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter8);
    }

    public void onSharedPreferencesActivity(View v) {
        startActivity(new Intent(this, SharedPreferencesActivity.class));
    }

    public void onChainedNetworkActivity(View v) {
        // TODO: 2019/9/20 Chained:连接
        startActivity(new Intent(this, ChainedNetworkActivity.class));
    }

    public void onChainedNetworkActivityCopy(View v) {
        // TODO: 2019/9/20 Chained:连接
        startActivity(new Intent(this, ChainedNetworkCopyActivity.class));
    }
}
