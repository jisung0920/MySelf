package com.example.jisung.myself;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
        final View diaV = View.inflate(this,R.layout.add_todo,null);
        if(v.getId()==R.id.addBnt){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            final DialogInterface mPopupDlg = dialog.setView(diaV).show();
            Button b1 = (Button)diaV.findViewById(R.id.addBnt);
            final EditText title = (EditText)diaV.findViewById(R.id.todoName);
            final DatePicker day = (DatePicker)diaV.findViewById(R.id.date);
            final TimePicker time = (TimePicker)diaV.findViewById(R.id.time);
            final CheckBox c1 = (CheckBox)diaV.findViewById(R.id.togoAlarm);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                    String date = day.getYear()+"-"+day.getMonth()+"-"+day.getDayOfMonth();
                    String t = time.getHour()+":"+time.getMinute();
                    String name = title.getText().toString();
                    Boolean check = c1.isChecked();
                    todo.add(new toDo(name,date,t,false,check));
                    adapter.notifyDataSetChanged();
                    mPopupDlg.dismiss();
                }
            });
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
