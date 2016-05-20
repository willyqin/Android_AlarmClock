package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Han on 2016/5/15.
 */
public class AlarmServiceBroadcasetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("nihao", "hello");
        Intent serviceIntent = new Intent(context,AlarmService.class);
        context.startService(serviceIntent);
    }
}
