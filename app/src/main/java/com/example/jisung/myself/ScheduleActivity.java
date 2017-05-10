package com.example.jisung.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    ListView list;
    ArrayList<toDo> todo;
    todoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        list = (ListView)findViewById(R.id.list);
        todo = new ArrayList<toDo>();
        adapter.locate=1;
        todo.add(new toDo("멀미","asdf","afsd",true,false));
        todo.add(new toDo("컴구","asdf","afsd",true,false));
        todo.add(new toDo("디비","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));

        adapter = new todoAdapter(this,todo);
        list.setAdapter(adapter);
    }
}
