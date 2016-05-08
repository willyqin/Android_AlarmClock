package com.example.han.myalarmclock;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Han on 2016/5/7.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Alarm[] mData;
    MyAdapter(Alarm[] mData) {
        this.mData = mData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView timeTextView;
        public TextView timeUntilTextView;
        public TextView repeatDays;
        public Switch aSwitch;
        ViewHolder(View v){
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
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.textView.setText(mData[position]);
        Alarm tempAlarm = mData[position];
        holder.timeTextView.setText(tempAlarm.getAlarmTimeString());
        holder.timeUntilTextView.setText("还有" + tempAlarm.getTimeUntilNextAlarmMessage());
        holder.repeatDays.setText(tempAlarm.getDaysString());
        holder.aSwitch.setChecked(tempAlarm.getAlarmActive());
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}
