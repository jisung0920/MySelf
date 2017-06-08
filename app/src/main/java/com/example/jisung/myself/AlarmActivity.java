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
import android.widget.AdapterView;
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
    TimePicker t1,t2;
    CheckBox c1,c2;
    ListView list;
    ArrayList<toDo> todo;
    manageDB db;
    AlarmHATT alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        tmp = getSharedPreferences("test", MODE_PRIVATE);
        editor = tmp.edit();
        init();
        alarm = new AlarmHATT(getApplicationContext());
//        if(c1.isChecked()){
//            int h = t1.getHour();
//            int m = t1.getMinute();
//            alarm.Alarm(h,m,1,"아침",1);
//        }
//        if(c2.isChecked()){
//            int h = t2.getHour();
//            int m = t2.getMinute();
//            alarm.Alarm(h,m,2,"하루 끝",2);
//        }
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Calendar calendar;
                    calendar = Calendar.getInstance();
                    int h = t1.getHour();
                    int m = t1.getMinute();
                    if(calendar.get(Calendar.HOUR_OF_DAY)>h)
                        alarm.Alarm(h,m,"하루 시작",0,1);
                    else
                        alarm.Alarm(h,m,"하루 시작",0,0);
                }
                else{
                    alarm.cancel(0);
                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int h = t2.getHour();
                    int m = t2.getMinute();
                    Calendar calendar;
                    calendar = Calendar.getInstance();
                    if(calendar.get(Calendar.HOUR_OF_DAY)>h)
                        alarm.Alarm(h,m,"하루 끝",2,1);
                    else
                        alarm.Alarm(h,m,"하루 끝",2,0);
                }
                else

                    alarm.cancel(2);
            }
        });



        final alramAdapter adapter = new alramAdapter(getApplicationContext(), todo);

        //todo.add(new toDo("계절학기 등록", "2017-06-06", "12:00", true, true));
        list.setAdapter(adapter);


    }
    public class AlarmHATT {
        private Context context;
        AlarmManager am;
        Intent intent;
        PendingIntent sender;
        Calendar calendar;

        public AlarmHATT(Context context) {
            this.context = context;
        }

        public void Alarm(int h, int m, String title, int id,int over) {
            am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(context, BroadcastD.class);
            intent.putExtra("title", title);
            sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+over, h, m, 0);//time set
            Log.d("gettime",calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.getTimeInMillis()+"");
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }

        public void cancel(int id) {
            if (sender != null) {
                am = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(context, BroadcastD.class);
                sender = PendingIntent.getBroadcast(context.getApplicationContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                am.cancel(sender);
                sender.cancel();
                sender = null;
                am = null;


            }

        }


    }
    void init() {
        db=new manageDB(this);
        list = (ListView) findViewById(R.id.list);
        t1 = (TimePicker)findViewById(R.id.moring);
        t2 = (TimePicker)findViewById(R.id.night);
        c1 = (CheckBox)findViewById(R.id.moringCheck);
        c2 = (CheckBox)findViewById(R.id.nightCheck);
        c1.setChecked(tmp.getBoolean("moring",false));
        c2.setChecked(tmp.getBoolean("night",false));
        t1.setHour(tmp.getInt("moring_h",8));
        t1.setMinute(tmp.getInt("moring_m",0));
        t2.setHour(tmp.getInt("night_h",22));
        t2.setMinute(tmp.getInt("night_m",0));
        todo = db.selecttoDo_A();

    }

    @Override
    public void onBackPressed() {
        editor.putBoolean("moring",c1.isChecked());
        editor.putBoolean("night", c2.isChecked());
        editor.putInt("moring_h",t1.getHour());
        editor.putInt("moring_m",t1.getMinute());
        editor.putInt("night_h",t1.getHour());
        editor.putInt("night_m",t1.getMinute());
        editor.commit();

        super.onBackPressed();
    }
}
