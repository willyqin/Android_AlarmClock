package com.example.han.myalarmclock;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


/**
 * Created by Han on 2016/5/15.
 */
public class AlertActivity extends Activity implements View.OnClickListener{
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Boolean alarmActive;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nihao", "create");
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.alert_activity_layout);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");

        TextView textView =(TextView) findViewById(R.id.alert_textview2);
        textView.setText(alarm.getAlarmText());
        editText = (EditText) findViewById(R.id.alert_textview);
        button = (Button) findViewById(R.id.alert_button);
        button.setOnClickListener(this);
//        Toast.makeText(this,"before media",Toast.LENGTH_LONG).show();
        mediaPlayer = new MediaPlayer();
        Log.d("nihao", "startAlert");
        try{
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.setDataSource(this,
                    Uri.parse(alarm.getAlarmTonePath()));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.d("nihao","play");
        } catch (IOException e) {
            mediaPlayer.release();
            alarmActive = false;
            Log.d("nihao","yichang");
        }


    }

    @Override
    protected void onResume() {
        alarmActive = true;
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!alarmActive) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StaticWakeLock.LockOff(this);
    }

    @Override
    protected void onDestroy() {
        try{
            mediaPlayer.stop();
        }catch (Exception e){

        }
        try{
            mediaPlayer.release();
        }catch (Exception e){

        }
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        if (isCorrect()) {
            alarmActive = false;
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException ise) {

            }
            try {
                mediaPlayer.release();
            } catch (Exception e) {

            }
            this.finish();
        }
    }

    private Boolean  isCorrect(){
        String stringAlarm = alarm.getAlarmText();
        String stringAlert = editText.getText().toString();
         return stringAlarm.equals(stringAlert);


    }
}
