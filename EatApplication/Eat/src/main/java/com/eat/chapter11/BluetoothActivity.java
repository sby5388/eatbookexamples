package com.eat.chapter11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eat.R;


public class BluetoothActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
    }

    public void onStartListening(View v) {
        Intent intent = BluetoothService.newIntent(this);
        intent.putExtra(BluetoothService.COMMAND_KEY, BluetoothService.COMMAND_START_LISTENING);
        startService(intent);
    }

    public void onStopListening(View v) {
        Intent intent = BluetoothService.newIntent(this);
        stopService(intent);
    }
}