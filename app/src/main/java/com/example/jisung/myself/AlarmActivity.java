package com.example.jisung.myself;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class AlarmActivity extends AppCompatActivity {

    Calendar Time;
    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;
    long now = System.currentTimeMillis();
    Date date = new Date(now);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "hh시 mm분 ss초");

    TextView textView;
//    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            Time.set(Calendar.YEAR,year);
//            Time.set(Calendar.MONTH,month);
//            Time.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//            updateLabel();
//        }
//    };
    private TimePickerDialog.OnTimeSetListener sTimeSetLitener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time.set(Calendar.HOUR_OF_DAY,hourOfDay);
            Time.set(Calendar.MINUTE,minute);
            Time.set(Calendar.SECOND,0);
            updateLabel();
        }
    };

    ListView list;
    CalendarView calendarView;
    ArrayList<toDo> todo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();
        final alramAdapter adapter = new alramAdapter(getApplicationContext(), todo);
        todo.add(new toDo("멀미", "asdf", "afsd", true, false));
        todo.add(new toDo("컴구", "asdf", "afsd", true, false));
        todo.add(new toDo("디비", "asdf", "afsd", true, true));

        todo.add(new toDo("데통", "asdf", "afsd", true, false));
        list.setAdapter(adapter);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                adapter.notifyDataSetChanged();
            }
        });

        Time = Calendar.getInstance();
        Button.OnClickListener bClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.setAlarm:
                        setAlarm();
                        break;
                    case R.id.removeAlarm:
                        removeAlarm();
                        break;
                    case R.id.button:
                        new TimePickerDialog(AlarmActivity.this,
                                sTimeSetLitener,Time.get(Calendar.HOUR_OF_DAY),Time.get(Calendar.MINUTE),false).show();
//                        new DatePickerDialog(AlarmActivity.this,
//                                eDateSetListener,
//                                Time.get(Calendar.YEAR),Time.get(Calendar.MONTH),
//                                Time.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                    case R.id.repeatAlarm:
                        setRepeatAlarm();
                        break;
                }
            }
        };
        findViewById(R.id.setAlarm).setOnClickListener(bClickListener);
        findViewById(R.id.removeAlarm).setOnClickListener(bClickListener);
        findViewById(R.id.button).setOnClickListener(bClickListener);
        findViewById(R.id.repeatAlarm).setOnClickListener(bClickListener);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        textView = (TextView)findViewById(R.id.textView);
        updateLabel();






        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");

        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");

        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");



// 지정된 포맷으로 String 타입 리턴


        String CurYear = CurYearFormat.format(date);

        String CurMonth = CurMonthFormat.format(date);

        String CurDay = CurDayFormat.format(date);
        Time.set(Calendar.YEAR,Integer.parseInt(CurYear));
        Time.set(Calendar.MONTH,Integer.parseInt(CurMonth));
        Time.set(Calendar.DAY_OF_MONTH,Integer.parseInt(CurDay));



    }
    private void updateLabel(){
        textView.setText(simpleDateFormat.format(Time.getTime()));
    }
    void setAlarm(){
        intent = new Intent("AlarmReceiver");

        ServicePending = PendingIntent.getBroadcast(AlarmActivity.this,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(),ServicePending);
        Toast.makeText(this, "알람설정", Toast.LENGTH_SHORT).show();
    }
    void removeAlarm(){
        intent = new Intent("AlarmReceiver");
        ServicePending = PendingIntent.getBroadcast(AlarmActivity.this,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Toast.makeText(this, "알람해제", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(ServicePending);
    }
    void setRepeatAlarm(){
        intent = new Intent("Alarm Receiver");
        ServicePending = PendingIntent.getBroadcast(AlarmActivity.this,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("servicePending:",""+ServicePending.toString());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,Time.getTimeInMillis(),20000,ServicePending);
        Toast.makeText(this, "알람설정정", Toast.LENGTH_SHORT).show();
    }

    void init() {
        calendarView = (CalendarView) findViewById(R.id.calen);
        list = (ListView) findViewById(R.id.list);

    }
}
