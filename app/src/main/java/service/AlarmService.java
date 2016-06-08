package service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.example.han.myalarmclock.Alarm;
import com.example.han.myalarmclock.AlertActivity;
import com.example.han.myalarmclock.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import Database.MyDataBaseHelper;

/**
 * Created by Han on 2016/5/15.
 */
public class AlarmService extends Service {

    private static final String TAG = "AlarmService";
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private NotificationManager notificationManager;
    private Notification notification;
    private Timer timer;
    private NotificationCompat.Builder builder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private Alarm getNext() {
        myDataBaseHelper = new MyDataBaseHelper(this, "Alarm.db", null, 1);
        db = myDataBaseHelper.getWritableDatabase();
        Set<Alarm> alarmQueue = new TreeSet<Alarm>(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm lhs, Alarm rhs) {
                int result = 0;
                long diff = lhs.getAlarmTime().getTimeInMillis() - rhs.getAlarmTime().getTimeInMillis();
                if (diff > 0) {
                    return 1;
                } else {
                    if (diff < 0) {
                        return -1;
                    }
                    return result;
                }
            }
        });
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = db.query("Alarm_Table", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
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
                    if (object instanceof Alarm.Day[]) {
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
                alarms.add(alarm);
            } while (cursor.moveToNext());
            cursor.close();

        }
        for (Alarm alarm : alarms) {
            if (alarm.getAlarmActive()) {
                alarmQueue.add(alarm);
            }
        }
        if (alarmQueue.iterator().hasNext()) {
            return alarmQueue.iterator().next();
        } else {
            return null;
        }
    }


    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Alarm alarm = getNext();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (alarm != null){
//            notification = new Notification(R.drawable.ic_alarm_white_36dp,"闹钟已创建",System.currentTimeMillis());
//            notification.setLatestEventInfo(getApplicationContext(), alarm.getAlarmTimeString(), "还有" + alarm.getTimeUntilNextAlarmMessage(), null);
            builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.ic_alarm_white_36dp);
            builder.setContentTitle(alarm.getAlarmTimeString());
            builder.setContentText("还有" + alarm.getTimeUntilNextAlarmMessage());
            builder.setOngoing(true);
            builder.setWhen(System.currentTimeMillis());
            notificationManager.notify(1, builder.build());
            alarm.schedule(getApplicationContext());

            timer = new Timer();
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x001){
                        Alarm mAlarm = getNext();
                        if (mAlarm != null) {
                            builder.setSmallIcon(R.drawable.ic_alarm_white_36dp);
                            builder.setContentTitle(mAlarm.getAlarmTimeString());
                            builder.setContentText("还有" + mAlarm.getTimeUntilNextAlarmMessage());
                            builder.setOngoing(true);
                            builder.setWhen(System.currentTimeMillis());
                            notificationManager.cancel(1);
                            notificationManager.notify(1, builder.build());
                        }else {
                            timer.cancel();
                        }
//                        Log.d(TAG, "handleMessage: run");
                    }
                }
            };
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 0x001;
                    handler.sendMessage(message);
                }
            },0,60000);

        }else {
            notificationManager.cancel(1);
            Intent mintent = new Intent(getApplicationContext(), AlertActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mintent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
        return START_NOT_STICKY;
    }
}
