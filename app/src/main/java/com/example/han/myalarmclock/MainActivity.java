package com.example.han.myalarmclock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.han.myalarmclock.MyAdapter mAdapter;
    TextView textView;
    RecyclerView.LayoutManager layoutManager;
    List<Alarm> alarmList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == 1){
            Alarm alarm = (Alarm)data.getSerializableExtra("AlarmSaved");
            int position = data.getIntExtra("AlarmSavedPosition",-1);
            alarmList.set(position,alarm);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView)findViewById(R.id.empty_image);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Alarm newAlarm = new Alarm();
                alarmList.add(newAlarm);
                mAdapter.notifyDataSetChanged();
                if (alarmList.size() == 1){
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        alarmList = new ArrayList<Alarm>();
        for (int i = 0; i < 6; i++){
            alarmList.add(new Alarm());
        }
        mAdapter = new com.example.han.myalarmclock.MyAdapter(alarmList,this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onItemClick(Alarm alarm,int position) {
                Bundle data = new Bundle();
                data.putSerializable("Alarm", alarm);
                Intent intent = new Intent(MainActivity.this,AlarmPropertyActivity.class);
                intent.putExtras(data);
                intent.putExtra("AlarmPosition",position);
                startActivityForResult(intent, 0);
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
                    textView.setVisibility(View.VISIBLE);
                }
                Snackbar.make(recyclerView, "已经成功删除", Snackbar.LENGTH_LONG)
                        .setAction("撤销删除", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alarmList.add(pos, tempAlarm);
                                        mAdapter.notifyItemInserted(pos);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        textView.setVisibility(View.INVISIBLE);
                                    }
                                }

                        ).show();

                        }
            };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
