package com.example.han.myalarmclock;

import android.app.Application;
import android.content.Context;

/**
 * Created by Han on 2016/5/20.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
