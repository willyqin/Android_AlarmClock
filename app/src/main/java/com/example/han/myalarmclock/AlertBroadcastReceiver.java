package com.example.han.myalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import service.AlarmServiceBroadcasetReceiver;

/**
 * Created by Han on 2016/5/16.
 */
public class AlertBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, AlarmServiceBroadcasetReceiver.class);
        context.sendBroadcast(mIntent);

        StaticWakeLock.LockOn(context);
        Bundle bundle = intent.getExtras();
        Alarm alarm = (Alarm)bundle.getSerializable("alarm");

        Intent alertActivityIntent = new Intent(context,AlertActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("alarm",alarm);
        alertActivityIntent.putExtras(mBundle);
        alertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alertActivityIntent);

    }
}
