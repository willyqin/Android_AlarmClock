package com.example.han.myalarmclock;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Han on 2016/5/7.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
    private List<Alarm> mData;
    private Context mcontext;
    private RecyclerViewClickListener recyclerViewClickListener = null;
    private SwitchCheckedListener switchCheckedListener = null;
    MyAdapter(List<Alarm> mData,Context context) {
        this.mData = mData;
        mcontext = context;
    }


    public void setOnSwtichCheckedListener(SwitchCheckedListener mswtichCheckedListener){
        switchCheckedListener = mswtichCheckedListener;
    }
    public void setOnItemClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
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
        final Alarm tempAlarm = mData.get(position);
        holder.timeTextView.setText(tempAlarm.getAlarmTimeString());
        holder.timeUntilTextView.setText("还有" + tempAlarm.getTimeUntilNextAlarmMessage());
        holder.repeatDays.setText(tempAlarm.getDaysString());
        holder.aSwitch.setChecked(tempAlarm.getAlarmActive());
        Integer pos;
        pos = position;
        holder.aSwitch.setTag(R.id.alarm_tag,tempAlarm);
        holder.aSwitch.setTag(R.id.position_tag,pos);
        holder.cardView.setTag(R.id.alarm_tag, tempAlarm);
        holder.cardView.setTag(R.id.position_tag,pos);
        holder.cardView.setOnClickListener(this);
//        new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle data = new Bundle();
//                data.putSerializable("Alarm", tempAlarm);
//                Intent intent = new Intent(mcontext,AlarmPropertyActivity.class);
//                intent.putExtras(data);
//                mcontext.startActivity(intent);
//
//            }
//        }


        holder.aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCheckedListener != null){
                    switchCheckedListener.onChecked((Alarm)v.getTag(R.id.alarm_tag),(Integer)v.getTag(R.id.position_tag),holder.timeUntilTextView);
                }
//                Switch tempSwitch = (Switch) v;
//                Alarm alarm = mData.get(position);
//                if (tempSwitch.isChecked()) {
//                    alarm.setAlarmActive(true);
//                    holder.timeUntilTextView.setText("还有" + mData.get(position).getTimeUntilNextAlarmMessage());
//                }else{
//                    alarm.setAlarmActive(false);
//                    holder.timeUntilTextView.setText("闹钟已关闭");
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static interface RecyclerViewClickListener{
        void onItemClick(Alarm alarm,int position);
    }

    public static  interface SwitchCheckedListener{
        void onChecked(Alarm alarm,int position,TextView textView);
    }

    @Override
    public void onClick(View v) {
        if (recyclerViewClickListener != null) {
            recyclerViewClickListener.onItemClick((Alarm)v.getTag(R.id.alarm_tag),(Integer)v.getTag(R.id.position_tag));
        }
    }

}
