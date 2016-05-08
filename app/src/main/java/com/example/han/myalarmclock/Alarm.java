package com.example.han.myalarmclock;

import android.media.RingtoneManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Han on 2016/4/13.
 */
public class Alarm {
    public enum Day{
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNSDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,;


        @Override
        public String toString() {
            switch (this.ordinal()){
                case 0:
                    return "日";
                case 1:
                    return "一";
                case 2:
                    return "二";
                case 3:
                    return "三";
                case 4:
                    return "四";
                case 5:
                    return "五";
                case 6:
                    return "六";
            }
            return super.toString();
        }
    }

    private int id;
    private Boolean alarmActive = true;
    private Calendar alarmTime = Calendar.getInstance();
    private Day[] days = {Day.SUNDAY,Day.MONDAY,Day.TUESDAY,Day.WEDNSDAY,Day.THURSDAY,Day.FRIDAY,Day.SATURDAY};
    private String alarmTonePath = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
    private Boolean vibrate = true;
    private String alarmName;
    private String alarmText = new String("请输入明天想喊醒自己的话");

    Alarm(){

    }
    public Boolean getAlarmActive(){
        return alarmActive;
    }

    public void setAlarmActive(Boolean alarmActive){
        this.alarmActive = alarmActive;
    }

    public Calendar getAlarmTime(){     //不仅要返回alarmTime属性，还有刷新alarmTime属性
        while (alarmTime.before(Calendar.getInstance())){
            alarmTime.add(Calendar.DAY_OF_MONTH,1);
        }
        while (!Arrays.asList(getDays()).contains(Day.values()[alarmTime.get(Calendar.DAY_OF_WEEK) - 1])){
            alarmTime.add(Calendar.DAY_OF_MONTH,1);
        }
        return alarmTime;
    }

    public String getAlarmTimeString(){
        String time = "";
        if (alarmTime.get(Calendar.HOUR_OF_DAY) <= 9)
            time += "0";
        time += String.valueOf(alarmTime.get(Calendar.HOUR_OF_DAY));
        time += ":";
        if (alarmTime.get(Calendar.MINUTE) <= 9)
            time +="0";
        time += String.valueOf(alarmTime.get(Calendar.MINUTE));
        return time;
    }

    public void setAlarmTime(Calendar alarmTime){
        this.alarmTime = alarmTime;
    }

    public void setAlarmTime(String alarmTime){
        String[] alarmTimeSplit = alarmTime.split(":");
        Calendar alarmTimeCal = Calendar.getInstance();
        alarmTimeCal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(alarmTimeSplit[0]));
        alarmTimeCal.set(Calendar.MINUTE,Integer.parseInt(alarmTimeSplit[1]));
        setAlarmTime(alarmTimeCal);

    }

    public Day[] getDays(){
        return days;
    }

    public void setDays(Day[] days){
        this.days = days;
    }

  /*  public void addDay(Day day){
        for (Day temp : this.days){
            if(temp == day)
                return;
        }

        boolean flag = false;
        Day[] days = new Day[this.days.length + 1];
        int i = 0;
        for (Day temp : this.days){
            if (temp.ordinal() > day.ordinal()&& !flag){
                days[i] = day;
                flag = true;
                i += 1;
            }
            days[i] = temp;
            i++;
        }
        setDays(days);
    }*/
    public void addDay(Day day){
        List<Day> daysList = new ArrayList<Day>();
        for (Day temp : days){
            if (temp.ordinal() == day.ordinal())
                return;
            daysList.add(temp);
        }
        daysList.add(day);
        setDays(daysList.toArray(new Day[daysList.size()]));
    }

   /* public void removeDay(Day day){
        Day[] days = new Day[this.days.length - 1];
        int i = 0;
        int j = 0;
        for(Day temp : this.days){
            if (temp.ordinal() != day.ordinal()){
                days[i] = this.days[j];
                i++;
                j++;
            }else {
                j++;
            }
        }
        setDays(days);
    }*/
    public void removeDay(Day day){
        List<Day> daysList = Arrays.asList(getDays());
        List<Day> dayRemoved = new ArrayList<Day>();
        for(Day temp : daysList){
            if (temp != day){
                dayRemoved.add(temp);
            }
        }
        setDays(dayRemoved.toArray(new Day[dayRemoved.size()]));
    }

    public String getAlarmTonePath(){
        return alarmTonePath;
    }

    public void setAlarmTonePath(String alarmTonePath){
        this.alarmTonePath = alarmTonePath;
    }

    public Boolean getVibrate(){
        return vibrate;
    }

    public void setVibrate(Boolean vibrate){
        this.vibrate = vibrate;
    }

    public String getAlarmName(){
        return alarmName;
    }

    public void setAlarmName(String alarmName){
        this.alarmName = alarmName;
    }

    public String getAlarmText(){
        return alarmText;
    }

    public void setAlarmText(String alarmText){
        this.alarmText = alarmText;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

  /*  public String getDaysString(){
        StringBuilder Days = new StringBuilder();
        List<Day> DaysList = Arrays.asList(days);
        for (Day tempDay : DaysList ){
            Days.append(tempDay.toString());
            Days.append( " ");
        }
        Days.setLength(Days.length() - 1);
        return Days.toString();
    }*/

    public  String getDaysString(){
        StringBuilder Days = new StringBuilder();
        if (days.length == 0){
            Days.append("闹钟已关闭");
            setAlarmActive(false);
        }else{
            Arrays.sort(days, new Comparator<Day>() {
                @Override
                public int compare(Day lhs, Day rhs) {
                    return lhs.ordinal() - rhs.ordinal();
                }
            });
            for (Day temp : days){
                Days.append(temp);
                Days.append(" ");
            }
        }
        Days.setLength(Days.length() - 1);
        return Days.toString();
    }

    public void schedule(){}//未完成

    public String getTimeUntilNextAlarmMessage(){
        String untilTime = new String("");
        long alarmTimeLong = getAlarmTime().getTimeInMillis();
        long temp = alarmTimeLong - System.currentTimeMillis();
        int hours;
        int min;
        if (temp >= 1*60*60*1000){
            hours =(int) (temp / 3600000);
            untilTime += String.valueOf(hours);
            untilTime += "小时";
            min =(int) ((temp - hours * 3600000) / 60000);
            untilTime += String.valueOf(min);
            untilTime += "分钟";
        }else {
            if (temp >= 60 * 1000){
                min = (int) (temp /6000);
                untilTime += String.valueOf(min);
                untilTime += "分钟";
            }else{
                untilTime += "少于一分钟";
            }
        }
        return untilTime;
    }
}
