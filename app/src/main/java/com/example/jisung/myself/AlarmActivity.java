package com.example.jisung.myself;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class AlarmActivity extends AppCompatActivity {

    SharedPreferences tmp;
    SharedPreferences.Editor editor;
    private static int ONE_MINUTE = 5626;
    private AlarmManager alarmManager;
    TimePicker t1,t2;
    CheckBox c1,c2;
    long now = System.currentTimeMillis();
    Date date = new Date(now);



    ListView list;
    CalendarView calendarView;
    ArrayList<toDo> todo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tmp = getSharedPreferences("test", MODE_PRIVATE);
        editor = tmp.edit();
        init();
        final AlarmHATT Malarm = new AlarmHATT(getApplicationContext());
        final AlarmHATT Nalarm = new AlarmHATT(getApplicationContext());

        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int h = t1.getHour();
                    int m = t1.getMinute();

                    Malarm.Alarm(h,m,1);
                }
                else{
                    Malarm.cancel();

                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int h = t2.getHour();
                    int m = t2.getMinute();
                    Nalarm.Alarm(h,m,2);
                }
                else

                    Nalarm.cancel();
            }
        });



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

    }
    public class AlarmHATT{
        private Context context;
        AlarmManager  am;
        Intent intent;
        PendingIntent sender;
        Calendar calendar;
        public AlarmHATT(Context context){
            this.context = context;

        }
        public void Alarm(int h,int m,int check){
            am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(AlarmActivity.this, BroadcastD.class);
            intent.putExtra("check",check);
            sender = PendingIntent.getBroadcast(AlarmActivity.this,0,intent,0);
            calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),h,m,0);//time set
            am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
           // am.cancel(sender);
        }
        public void cancel(){
            am.cancel(sender);
        }

    }



    void init() {
        calendarView = (CalendarView) findViewById(R.id.calen);
        list = (ListView) findViewById(R.id.list);
        t1 = (TimePicker)findViewById(R.id.moring);
        t2 = (TimePicker)findViewById(R.id.night);
        c1 = (CheckBox)findViewById(R.id.moringCheck);
        c2 = (CheckBox)findViewById(R.id.nightCheck);
        c1.setChecked(tmp.getBoolean("moring",false));
        c2.setChecked(tmp.getBoolean("night",false));
    }

    @Override
    public void onBackPressed() {
        editor.putBoolean("moring",c1.isChecked());
        editor.putBoolean("night", c2.isChecked());
        editor.commit();

        super.onBackPressed();
    }
}
