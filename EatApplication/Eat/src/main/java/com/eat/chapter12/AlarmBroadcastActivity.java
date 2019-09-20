package com.eat.chapter12;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eat.R;


public class AlarmBroadcastActivity extends AppCompatActivity {

    private static final String ACTION_CUSTOM_ALARM_SERVICE = "com.eat.alarm_receiver";
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private TextView tvStatus;

    private AlarmManager am;

    private BroadcastReceiver alarmReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_broadcast);
        tvStatus = (TextView) findViewById(R.id.text_alarm_status);

        alarmReceiver = new AlarmReceiver();

        registerReceiver(alarmReceiver, new IntentFilter(ACTION_CUSTOM_ALARM_SERVICE));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_CUSTOM_ALARM_SERVICE), PendingIntent.FLAG_UPDATE_CURRENT);
        am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()
                + ONE_HOUR, ONE_HOUR, pendingIntent);

        final String alarmIsSet = "Alarm is set";
        tvStatus.setText(alarmIsSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }
}