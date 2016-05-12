package com.example.han.myalarmclock;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Han on 2016/5/7.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Alarm> mData;
    private Context mcontext;
    MyAdapter(List<Alarm> mData,Context context) {
        this.mData = mData;
        mcontext = context;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView timeTextView;
        public TextView timeUntilTextView;
        public TextView repeatDays;
        public Switch aSwitch;
        ViewHolder(final View v,final List<Alarm> list,final Context context ){
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            timeTextView = (TextView)v.findViewById(R.id.time_text_view);
            timeUntilTextView =(TextView) v.findViewById(R.id.time_until_text_view);
            repeatDays = (TextView) v.findViewById(R.id.repeatdays_textview);
            aSwitch =(Switch) v.findViewById(R.id.alarm_switch);
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mData,mcontext);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.textView.setText(mData[position]);
        Alarm tempAlarm = mData.get(position);
        holder.timeTextView.setText(tempAlarm.getAlarmTimeString());
        holder.timeUntilTextView.setText("还有" + tempAlarm.getTimeUntilNextAlarmMessage());
        holder.repeatDays.setText(tempAlarm.getDaysString());
        holder.aSwitch.setChecked(tempAlarm.getAlarmActive());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm tempAlarm = mData.get(position);
                Bundle data = new Bundle();
                data.putSerializable("Alarm", tempAlarm);
                Intent intent = new Intent(mcontext,AlarmPropertyActivity.class);
                intent.putExtras(data);
                mcontext.startActivity(intent);

            }
        });

        holder.aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch tempSwitch = (Switch) v;
                Alarm alarm = mData.get(position);
                if (tempSwitch.isChecked()) {
                    alarm.setAlarmActive(true);
                    holder.timeUntilTextView.setText("还有" + mData.get(position).getTimeUntilNextAlarmMessage());
                }else{
                    alarm.setAlarmActive(false);
                    holder.timeUntilTextView.setText("闹钟已关闭");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
