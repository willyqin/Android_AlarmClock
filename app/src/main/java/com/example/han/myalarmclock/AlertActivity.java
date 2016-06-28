package com.example.han.myalarmclock;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.show.api.ShowApiRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * Created by Han on 2016/5/15.
 */
public class AlertActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "AlertActivity";
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Boolean alarmActive;
    private EditText editText;
    private Button button;
    private int similarNun;
    private String stringAlarm;
    private String stringAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.setBackgroundDrawableResource(R.drawable.alert_bg);
        window.setStatusBarColor(getResources().getColor(R.color.transparent));
        setContentView(R.layout.alert_activity_layout);

//        Bundle bundle = this.getIntent().getExtras();
//        alarm = (Alarm) bundle.getSerializable("alarm");
        alarm = (Alarm) this.getIntent().getParcelableExtra("alarm");

        TextView textView =(TextView) findViewById(R.id.alert_textview2);
        textView.setText(alarm.getAlarmText());
        textView.setBackground(getResources().getDrawable(R.drawable.alert_bg));

        editText = (EditText) findViewById(R.id.alert_textview);
        editText.setBackground(getResources().getDrawable(R.drawable.alert_bg));
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

    class RtnTread implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            String appid = "21010";
            String secret = "8787a55f33cc48b1bf93532832fee3d4";
            final String res = new ShowApiRequest("http://route.showapi.com/294-1",appid,secret)
                    .addTextPara("t1",stringAlarm).addTextPara("t2", stringAlert).post();
            System.out.println(res);

            Log.d(TAG, res + new Date());

            similarNun = parseJSON(res);
            Log.d(TAG, "call: similarNum" + similarNun);
            return similarNun;
        }
    }
    private Boolean  isCorrect(){
        stringAlarm = alarm.getAlarmText();
        stringAlert = editText.getText().toString();

        RtnTread rtnTread = new RtnTread();
        FutureTask<Integer> task = new FutureTask<Integer>(rtnTread);

        new Thread(task).start();
        try {
            Log.d(TAG, "isCorrect: " + task.get());
            return task.get() > 0.5 ? true:false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private int parseJSON(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String showapi_res_code = jsonObject.getString("showapi_res_code");
            String showapi_res_error = jsonObject.getString("showapi_res_error");
            String showapi_res_body = jsonObject.getString("showapi_res_body");

            JSONObject jsonObjectBody = new JSONObject(showapi_res_body);
            String likeNum = jsonObjectBody.getString("like");
            Log.d(TAG, "parseJSON: likeNum: " + likeNum);
            return Double.parseDouble(likeNum) > 0.5 ? 1 : 0;
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "parseJSON: Exception");
        }
        return -1;
    }
}
