package com.example.jisung.myself;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    ListView list;
    CalendarView calendarView;
    ArrayList<toDo> todo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();
        final alramAdapter adapter = new alramAdapter(getApplicationContext(),todo);
        todo.add(new toDo("멀미","asdf","afsd",true,false));
        todo.add(new toDo("컴구","asdf","afsd",true,false));
        todo.add(new toDo("디비","asdf","afsd",true,true));

        todo.add(new toDo("데통","asdf","afsd",true,false));
        list.setAdapter(adapter);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    void init(){
        calendarView = (CalendarView)findViewById(R.id.calen);
        list = (ListView)findViewById(R.id.list);

    }
}
