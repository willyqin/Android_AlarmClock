package com.example.han.myalarmclock;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Han on 2016/5/9.
 */
public class AlarmPropertyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TimePicker timePicker;
    private Alarm alarm;
    private TextView untilTimeText;
    private TextView onRepeat;
    private TextView ri;
    private TextView yi;
    private TextView er;
    private TextView san;
    private TextView si;
    private TextView wu;
    private TextView liu;
    private TextView norepeat;
    private Intent intent;
    private int position;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_property_activity);

        intent = getIntent();
        alarm = (Alarm) intent.getSerializableExtra("Alarm");
        position = intent.getIntExtra("AlarmPosition",-1);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        String[] time = alarm.getAlarmTimeString().split(":");
        timePicker.setCurrentHour(Integer.parseInt(time[0]));
        timePicker.setCurrentMinute(Integer.parseInt(time[1]));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarm.setAlarmTime(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
            }
        });

        untilTimeText = (TextView) findViewById(R.id.until_text_view_property);
        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());

        ri = (TextView) findViewById(R.id.ri);
        yi = (TextView) findViewById(R.id.yi);
        er = (TextView) findViewById(R.id.er);
        san = (TextView) findViewById(R.id.san);
        si = (TextView) findViewById(R.id.si);
        wu = (TextView) findViewById(R.id.wu);
        liu = (TextView) findViewById(R.id.liu);
        norepeat = (TextView) findViewById(R.id.no_repeat);


        final List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
        if (tempList.contains(Alarm.Day.SUNDAY)) {
            ri.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.MONDAY)) {
            yi.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.TUESDAY)) {
            er.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.WEDNSDAY)) {
            san.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.THURSDAY)) {
            si.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.FRIDAY)) {
            wu.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (tempList.contains(Alarm.Day.SATURDAY)) {
            liu.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if (!alarm.getRepeatFlag()) {
            norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.SUNDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.SUNDAY);
                        alarm.setRepeatFlag(false);
                        ri.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.SUNDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        ri.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.SUNDAY);
                        alarm.setRepeatFlag(true);
                        ri.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.SUNDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        ri.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });

        yi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.MONDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.MONDAY);
                        alarm.setRepeatFlag(false);
                        yi.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.MONDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        yi.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.MONDAY);
                        alarm.setRepeatFlag(true);
                        yi.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.MONDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        yi.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }

        });

        er.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.TUESDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.TUESDAY);
                        alarm.setRepeatFlag(false);
                        er.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.TUESDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        er.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.TUESDAY);
                        alarm.setRepeatFlag(true);
                        er.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.TUESDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        er.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });

        san.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.WEDNSDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.WEDNSDAY);
                        alarm.setRepeatFlag(false);
                        san.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.WEDNSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        san.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.WEDNSDAY);
                        alarm.setRepeatFlag(true);
                        san.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.WEDNSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        san.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.THURSDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.THURSDAY);
                        alarm.setRepeatFlag(false);
                        si.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.THURSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        si.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.THURSDAY);
                        alarm.setRepeatFlag(true);
                        si.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.THURSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        si.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });
        wu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.FRIDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.FRIDAY);
                        alarm.setRepeatFlag(false);
                        wu.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.FRIDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        wu.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.FRIDAY);
                        alarm.setRepeatFlag(true);
                        wu.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.FRIDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        wu.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });

        liu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.SATURDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.SATURDAY);
                        alarm.setRepeatFlag(false);
                        liu.setBackgroundColor(getResources().getColor(R.color.transparent));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.SATURDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        liu.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.SATURDAY);
                        alarm.setRepeatFlag(true);
                        liu.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.SATURDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        liu.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });

        norepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noRepeat();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.property_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_alarm:
                Bundle data = new Bundle();
                data.putSerializable("AlarmSaved",alarm);
                intent.putExtras(data);
                intent.putExtra("AlarmSavedPosition", position);
                AlarmPropertyActivity.this.setResult(1, intent);
                AlarmPropertyActivity.this.finish();
                break;
            case R.id.hours_change:
                if (timePicker.is24HourView()) {
                    timePicker.setIs24HourView(false);
                } else {
                    timePicker.setIs24HourView(true);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    private void noRepeat(){
        if (alarm.getRepeatFlag() == false) {

            ri.setBackgroundColor(getResources().getColor(R.color.colorGray));
            yi.setBackgroundColor(getResources().getColor(R.color.colorGray));
            er.setBackgroundColor(getResources().getColor(R.color.colorGray));
            san.setBackgroundColor(getResources().getColor(R.color.colorGray));
            si.setBackgroundColor(getResources().getColor(R.color.colorGray));
            wu.setBackgroundColor(getResources().getColor(R.color.colorGray));
            liu.setBackgroundColor(getResources().getColor(R.color.colorGray));

            alarm.addDay(Alarm.Day.SUNDAY);
            alarm.addDay(Alarm.Day.SATURDAY);
            alarm.addDay(Alarm.Day.FRIDAY);
            alarm.addDay(Alarm.Day.THURSDAY);
            alarm.addDay(Alarm.Day.WEDNSDAY);
            alarm.addDay(Alarm.Day.TUESDAY);
            alarm.addDay(Alarm.Day.MONDAY);

            alarm.setRepeatFlag(true);

            alarm.getAlarmTime();
            untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
            norepeat.setBackgroundColor(getResources().getColor(R.color.transparent));
        }else {
            ri.setBackgroundColor(getResources().getColor(R.color.transparent));
            yi.setBackgroundColor(getResources().getColor(R.color.transparent));
            er.setBackgroundColor(getResources().getColor(R.color.transparent));
            san.setBackgroundColor(getResources().getColor(R.color.transparent));
            si.setBackgroundColor(getResources().getColor(R.color.transparent));
            wu.setBackgroundColor(getResources().getColor(R.color.transparent));
            liu.setBackgroundColor(getResources().getColor(R.color.transparent));

            alarm.removeDay(Alarm.Day.SUNDAY);
            alarm.removeDay(Alarm.Day.SATURDAY);
            alarm.removeDay(Alarm.Day.FRIDAY);
            alarm.removeDay(Alarm.Day.THURSDAY);
            alarm.removeDay(Alarm.Day.WEDNSDAY);
            alarm.removeDay(Alarm.Day.TUESDAY);
            alarm.removeDay(Alarm.Day.MONDAY);

            alarm.setRepeatFlag(false);

            alarm.getAlarmTime();
            untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
            norepeat.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }

}
