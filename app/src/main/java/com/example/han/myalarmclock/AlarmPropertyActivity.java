package com.example.han.myalarmclock;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Han on 2016/5/9.
 */
public class AlarmPropertyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_property_activity);
        timePicker =(TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        toolbar =(Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.property_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save_alarm:
                break;
            case R.id.hours_change:
                if (timePicker.is24HourView()) {
                    timePicker.setIs24HourView(false);
                }else{
                    timePicker.setIs24HourView(true);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
