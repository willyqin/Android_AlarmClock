package com.example.han.myalarmclock;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Han on 2016/5/15.
 */
public class StaticWakeLock  {
    private static PowerManager.WakeLock wakeLock = null;
    public static void LockOn(Context context){
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (wakeLock == null)
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK,"AlertAlarm");
        wakeLock.acquire();
    }

    public static void LockOff(Context context){
        try {
            if (wakeLock != null){
                wakeLock.release();
            }
        } catch (Exception e){

        }
    }
}
