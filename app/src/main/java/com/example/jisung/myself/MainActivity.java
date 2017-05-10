package com.example.jisung.myself;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    GridView list;
    todoAdapter adapter;
    ArrayList<toDo> todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (GridView)findViewById(R.id.list);
        todo = new ArrayList<toDo>();
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
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todo.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    void onClick(View v){
        View diaV = View.inflate(this,R.layout.add_todo,null);
        if(v.getId()==R.id.addBnt){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(diaV).show();
        }
        else if(v.getId()==R.id.togoTimer){
            Intent intent = new Intent(this,FomodoroActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.togoAlarm){
            Intent intent = new Intent(this,AlarmActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.togoSche){
            Intent intent = new Intent(this,ScheduleActivity.class);
            startActivity(intent);
        }
    }
}
