package com.example.jisung.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    ListView list;
    ArrayList<toDo> todo;
    schAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        list = (ListView)findViewById(R.id.list);
        todo = new ArrayList<toDo>();
        todo.add(new toDo("DB","0900-1030","afsd",true,false));
        todo.add(new toDo("논리학","1100-1200","afsd",true,false));
        todo.add(new toDo("공모전 미팅","1300-1500","afsd",true,false));
        todo.add(new toDo("실습","1730-1900","afsd",true,false));


        adapter = new schAdapter(this,todo);
        list.setAdapter(adapter);
    }
}
