package com.example.han.myalarmclock;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Database.MyDataBaseHelper;
import service.AlarmServiceBroadcasetReceiver;


public class MainActivity extends AppCompatActivity {


    private MyDataBaseHelper mDataBaseHelper;
    private SQLiteDatabase db;
    RecyclerView recyclerView;
    com.example.han.myalarmclock.MyAdapter mAdapter;
    TextView emptyView;
    RecyclerView.LayoutManager layoutManager;
    List<Alarm> alarmList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == 1){
//            Alarm alarm = (Alarm)data.getSerializableExtra("AlarmSaved");
            Alarm alarm = (Alarm) data.getParcelableExtra("AlarmSaved");
//            Log.d("nihao","保存的铃声名字" + alarm.getRingToneName());
//            Log.d("nihao","保存的铃声的path" + alarm.getAlarmTonePath());
            int position = data.getIntExtra("AlarmSavedPosition",-1);
            alarm.setAlarmActive(true);
            alarmList.set(position, alarm);     //数据更新 ok
            mAdapter.notifyDataSetChanged();
            emptyView.setVisibility(View.INVISIBLE);

        }
        if (requestCode == 1 && resultCode == 1){
            recyclerView.setVisibility(View.VISIBLE);
//            Alarm alarm = (Alarm)data.getSerializableExtra("AlarmSaved");
            Alarm alarm = (Alarm) data.getParcelableExtra("AlarmSaved");
//            Log.d("nihao","保存的铃声名字" + alarm.getRingToneName());
//            Log.d("nihao", "保存的铃声的path" + alarm.getAlarmTonePath());
            alarmList.add(alarm);                //数据添加 ok
            mAdapter.notifyDataSetChanged(); //mark
            emptyView.setVisibility(View.INVISIBLE);

        }
        if (requestCode == 0 && resultCode == 0){
            int position = data.getIntExtra("AlarmSavedPosition",-1);
            alarmList.remove(position);               //数据删除
            mAdapter.notifyDataSetChanged();
            if (position == 0) emptyView.setVisibility(View.VISIBLE);

        }
        if(requestCode == 1 && resultCode == 0){        //fab启动的活动，返回失败

        }
        updataDatabase();
        callAlarmServiceBroadcastReceiver();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeUtile.selectTheme(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        callAlarmServiceBroadcastReceiver();

        mDataBaseHelper  = new MyDataBaseHelper(this,"Alarm.db",null,1);
        db = mDataBaseHelper.getWritableDatabase();
        alarmList = new ArrayList<Alarm>();
        Cursor cursor = db.query("Alarm_Table",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Alarm alarm = new Alarm();
                alarm.setAlarmActive(cursor.getInt(cursor.getColumnIndex("alarm_active")) == 1);
                alarm.setAlarmText(cursor.getString(cursor.getColumnIndex("alarm_text")));
                alarm.setAlarmTime(cursor.getString(cursor.getColumnIndex("alarm_time")));

                byte[] repeatDaysBytes = cursor.getBlob(cursor.getColumnIndex("alarm_days"));
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(repeatDaysBytes);
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Alarm.Day[] repeatDays;
                    Object object = objectInputStream.readObject();
                    if(object instanceof Alarm.Day[]){
                        repeatDays = (Alarm.Day[]) object;
                        alarm.setDays(repeatDays);
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                alarm.setAlarmTonePath(cursor.getString(cursor.getColumnIndex("alarm_tonepath")));
                alarm.setRingToneName(cursor.getString(cursor.getColumnIndex("alarm_tonename")));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
            cursor.close();
        }

//        Log.d("nihao","MainActivity onCreate afterCursor");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_alarm_white_36dp);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_theme:
                        startActivity(new Intent(MainActivity.this,SelectTheme.class));
                        break;
                    case R.id.action_presentation:
                        Alarm alarm = new Alarm();
                        alarm.setAlarmText("你好，测试");
                        Intent alertActivityIntent = new Intent(MainActivity.this,AlertActivity.class);
                        alertActivityIntent.putExtra("alarm", alarm);
                        alertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(alertActivityIntent);
                        break;
                }
                return false;
            }
        });

        emptyView = (TextView)findViewById(R.id.empty_image);
        if (alarmList.size() != 0){
            emptyView.setVisibility(View.INVISIBLE);
        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("nihao","before MainActivity FAB click");
                Alarm newAlarm = new Alarm();
//                Log.d("nihao","after MainActivity FAB click");
//                Bundle data = new Bundle();
//                data.putSerializable("Alarm", newAlarm);
                Intent intent = new Intent(MainActivity.this, AlarmPropertyActivity.class);
                intent.putExtra("Alarm", newAlarm);
                startActivityForResult(intent, 1);
//                alarmList.add(newAlarm);
//                mAdapter.notifyDataSetChanged();
//                if (alarmList.size() == 1){
//                    recyclerView.setVisibility(View.VISIBLE);
//                    textView.setVisibility(View.INVISIBLE);
//                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new com.example.han.myalarmclock.MyAdapter(alarmList,this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onItemClick(Alarm alarm, int position) {
//                Bundle data = new Bundle();
//                data.putSerializable("Alarm", alarm);
                Intent intent = new Intent(MainActivity.this, AlarmPropertyActivity.class);
                intent.putExtra("Alarm", alarm);
                intent.putExtra("AlarmPosition", position);
                startActivityForResult(intent, 0);
            }
        });
        mAdapter.setOnSwtichCheckedListener(new MyAdapter.SwitchCheckedListener() {
            @Override
            public void onChecked(Alarm alarm,int pos,TextView textView) {
                if(alarm.getAlarmActive() == true){
                    alarm.setAlarmActive(false);
                    textView.setText("闹钟已关闭");
                    updataDatabase();
                    callAlarmServiceBroadcastReceiver();

                }else {
                    alarm.setAlarmActive(true);
                    textView.setText("还有" + alarm.getTimeUntilNextAlarmMessage());
                    updataDatabase();
                    callAlarmServiceBroadcastReceiver();

                }
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
                final Alarm tempAlarm = alarmList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                if(alarmList.size() == 0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                updataDatabase();
                callAlarmServiceBroadcastReceiver();

                Snackbar.make(recyclerView, "已经成功删除", Snackbar.LENGTH_LONG)
                        .setAction("撤销删除", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alarmList.add(pos, tempAlarm);
                                        mAdapter.notifyItemInserted(pos);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        emptyView.setVisibility(View.INVISIBLE);
                                        updataDatabase();
                                        callAlarmServiceBroadcastReceiver();

                                    }
                                }

                        ).show();

                        }
            };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123){
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what=0x123;
                handler.sendMessage(msg);
            }
        }, 0 , 60000);
//        Log.d("nihao", "MainActivity onCreate over");
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    private void updataDatabase(){
        db.delete("Alarm_Table", "1", null);
        ContentValues values = new ContentValues();
        for (Alarm alarm : alarmList){

            values.put("alarm_active",alarm.getAlarmActive());
            values.put("alarm_time",alarm.getAlarmTimeString());

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                oos = new ObjectOutputStream(bos);
                oos.writeObject(alarm.getDays());
                byte[] buff = bos.toByteArray();

                values.put("alarm_days", buff);

            } catch (Exception e){
            }
            values.put("alarm_text",alarm.getAlarmText());
            values.put("alarm_tonepath",alarm.getAlarmTonePath());
            values.put("alarm_tonename",alarm.getRingToneName());
            db.insert("Alarm_Table",null,values);
            values.clear();
        }
    }
    public void callAlarmServiceBroadcastReceiver(){

        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcasetReceiver.class);
        sendBroadcast(mathAlarmServiceIntent);
    }


}
