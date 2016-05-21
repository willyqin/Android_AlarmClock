package com.example.han.myalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Han on 2016/5/9.
 */
public class AlarmPropertyActivity extends AppCompatActivity{
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
    private TextView delete;
    private EditText inputWords;
    private Intent intent;
    private int position;
    private int gray;
    private int fontColor;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_property_activity);

        init();
        setTimer();


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
    public void onBackPressed() {
//        Toast.makeText(AlarmPropertyActivity.this,"hello",Toast.LENGTH_SHORT).show();
        setResult(2,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_alarm:
                if (inputWords.getText().toString() != "") {
                    alarm.setAlarmText(inputWords.getText().toString());
                    Bundle data = new Bundle();
                    data.putSerializable("AlarmSaved", alarm);
                    intent.putExtras(data);
                    intent.putExtra("AlarmSavedPosition", position);
                    AlarmPropertyActivity.this.setResult(1, intent);
                    AlarmPropertyActivity.this.finish();
                }
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

            ri.setTextColor(fontColor);
            yi.setTextColor(fontColor);
            er.setTextColor(fontColor);
            san.setTextColor(fontColor);
            si.setTextColor(fontColor);
            wu.setTextColor(fontColor);
            liu.setTextColor(fontColor);

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
            norepeat.setTextColor(gray);
        }else {
            ri.setTextColor(gray);
            yi.setTextColor(gray);
            er.setTextColor(gray);
            san.setTextColor(gray);
            si.setTextColor(gray);
            wu.setTextColor(gray);
            liu.setTextColor(gray);

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
            norepeat.setTextColor(fontColor);
        }
    }

    private void init(){
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
//                Log.d("nihao",""+hourOfDay);
//                Log.d("nihao",""+minute);
                alarm.setAlarmTime(hourOfDay + ":" + minute);
//                Log.d("nihao",alarm.getAlarmTimeString());
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
        inputWords = (EditText) findViewById(R.id.input_word);
        norepeat = (TextView) findViewById(R.id.no_repeat);

        inputWords.setText(alarm.getAlarmText());


        gray = getResources().getColor(R.color.colorGray);
        fontColor = getResources().getColor(R.color.colorFont);
        final List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
        if (!tempList.contains(Alarm.Day.SUNDAY)) {
            ri.setTextColor(gray);
        }else {
            ri.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.MONDAY)) {
            yi.setTextColor(gray);
        }else{
            yi.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.TUESDAY)) {
            er.setTextColor(gray);
        }else{
            er.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.WEDNSDAY)) {
            san.setTextColor(gray);
        }else{
            san.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.THURSDAY)) {
            si.setTextColor(gray);
        }else{
            si.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.FRIDAY)) {
            wu.setTextColor(gray);
        }else{
            wu.setTextColor(fontColor);
        }
        if (!tempList.contains(Alarm.Day.SATURDAY)) {
            liu.setTextColor(gray);
        }else{
            liu.setTextColor(fontColor);
        }
        if (alarm.getRepeatFlag()) {
            norepeat.setTextColor(gray);
        }else {
            norepeat.setTextColor(fontColor);
        }

        ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Alarm.Day> tempList = Arrays.asList(alarm.getDays());
                if (tempList.contains(Alarm.Day.SUNDAY)) {
                    if (alarm.getDays().length == 1){
                        alarm.removeDay(Alarm.Day.SUNDAY);
                        alarm.setRepeatFlag(false);
                        ri.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.SUNDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        ri.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.SUNDAY);
                        alarm.setRepeatFlag(true);
                        ri.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.SUNDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        ri.setTextColor(fontColor);
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
                        yi.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.MONDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        yi.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.MONDAY);
                        alarm.setRepeatFlag(true);
                        yi.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.MONDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        yi.setTextColor(fontColor);
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
                        er.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.TUESDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        er.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.TUESDAY);
                        alarm.setRepeatFlag(true);
                        er.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.TUESDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        er.setTextColor(fontColor);
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
                        san.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.WEDNSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        san.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.WEDNSDAY);
                        alarm.setRepeatFlag(true);
                        san.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.WEDNSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        san.setTextColor(fontColor);
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
                        si.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.THURSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        si.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.THURSDAY);
                        alarm.setRepeatFlag(true);
                        si.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.THURSDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        si.setTextColor(fontColor);
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
                        wu.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.FRIDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        wu.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.FRIDAY);
                        alarm.setRepeatFlag(true);
                        wu.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.FRIDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        wu.setTextColor(fontColor);
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
                        liu.setTextColor(gray);
                        norepeat.setTextColor(fontColor);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.removeDay(Alarm.Day.SATURDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        liu.setTextColor(gray);
                    }
                }else {
                    if (alarm.getDays().length == 0){
                        alarm.addDay(Alarm.Day.SATURDAY);
                        alarm.setRepeatFlag(true);
                        liu.setTextColor(fontColor);
                        norepeat.setTextColor(gray);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    }else {
                        alarm.addDay(Alarm.Day.SATURDAY);
                        alarm.getAlarmTime();
                        untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                        liu.setTextColor(fontColor);
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

        delete = (TextView) findViewById(R.id.delete_alarm);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable("AlarmSaved",alarm);
                intent.putExtras(data);
                intent.putExtra("AlarmSavedPosition", position);
                AlarmPropertyActivity.this.setResult(0, intent);
                AlarmPropertyActivity.this.finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AlarmPropertyActivity.this,"hello",Toast.LENGTH_SHORT).show();
                AlarmPropertyActivity.this.setResult(2,intent);
                finish();
            }
        });
    }

    private void setTimer(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x1233) {
                    alarm.getAlarmTime();
                    untilTimeText.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0x1233;
                handler.sendMessage(msg);
            }
        },0,60000);
    }
}
