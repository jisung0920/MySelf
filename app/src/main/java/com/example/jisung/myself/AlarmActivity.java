package com.example.jisung.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ListView list = (ListView)findViewById(R.id.list);
        ArrayList<String> data = new ArrayList<>();
        data.add("멀티미디어 정보처리");
        data.add("컴퓨터 구조론");
        data.add("데이터 베이스");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice,data);
        list.setAdapter(adapter);
    }
}
