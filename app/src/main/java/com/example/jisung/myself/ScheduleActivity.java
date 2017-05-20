package com.example.jisung.myself;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    ListView list;
    ListView[] daylist = new ListView[5];
    ArrayList<Schedule>[] schData = new ArrayList[6];
    schAdapter[] adapter = new schAdapter[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        list = (ListView)findViewById(R.id.list);

        for(int i=0;i<6;i++)
            schData[i] = new ArrayList<Schedule>();

        int i=5;

        schData[i].add(new Schedule("0900-1030","DB","afsd",1));
        adapter[i] = new schAdapter(this,schData[i]);
        list.setAdapter(adapter[i]);
    }

    public void onClick(View v){
        if(v.getId()==R.id.addBtn){

        }
        else if(v.getId()==R.id.insertBtn){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            final View V = View.inflate(this,R.layout.time_table,null);

            daylist[0] = (ListView)V.findViewById(R.id.list0);
            daylist[1] = (ListView)V.findViewById(R.id.list1);
            daylist[2] = (ListView)V.findViewById(R.id.list2);
            daylist[3] = (ListView)V.findViewById(R.id.list3);
            daylist[4] = (ListView)V.findViewById(R.id.list4);
            for(int i=0;i<5;i++){
                schData[i].add(new Schedule("0900-1030","DB","afsd",1));
                adapter[i] = new schAdapter(this,schData[i]);
                daylist[i].setAdapter(adapter[i]);
            }
            dialog.setView(R.layout.time_table).show();
        }
        else if(v.getId()==R.id.delBtn){

        }

    }
}
