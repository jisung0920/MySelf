package com.example.jisung.myself;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    GridView list;
    todoAdapter adapter;
    ArrayList<toDo> todo;
    SharedPreferences tmp;
    SharedPreferences.Editor editor;
    AlarmHATT alarm;
    private static final String CONSUMER_KEY = "jisung0920-2994";
    private static final String CONSUMER_SECRET = "2fd26f8b8197436b";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;
    EvernoteSession mEvernoteSession;
    manageDB db;
    NewAppWidget widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widget = new NewAppWidget();
        init();

        String str ="";
        for(int i=0;i<todo.size();i++)
            str+="-"+todo.get(i).getTitle()+"\n";

        editor.putString("first", str);
        editor.commit();
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(MainActivity.this,NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        MainActivity.this.sendBroadcast(intent);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, todo.get(position).getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alarm.cancel(todo.get(position).id);
                db.delete(todo.get(position).id);
                todo.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });



    }

    void init() {
        db = new manageDB(this);


        tmp = getSharedPreferences("test", MODE_PRIVATE);
        editor = tmp.edit();
        alarm = new AlarmHATT(getApplicationContext());
        mEvernoteSession = new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(true)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();
        Boolean init = tmp.getBoolean("init", false);
        if (!init) {
            Toast.makeText(this, "인증을 하셔야 동기화를 할 수 있습니다.", Toast.LENGTH_SHORT).show();
            mEvernoteSession.authenticate(this);
            if (mEvernoteSession.isLoggedIn()) {
                editor.putBoolean("init", true);
                editor.commit();
            }
        }
        list = (GridView) findViewById(R.id.list);
        todo =db.selecttoDo_M();

        adapter = new todoAdapter(this, todo);
        list.setAdapter(adapter);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EvernoteSession.REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    void onClick(View v) {

        final View diaV = View.inflate(this, R.layout.add_todo, null);
        if (v.getId() == R.id.addBnt) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            final DialogInterface mPopupDlg = dialog.setView(diaV).show();
            Button b1 = (Button) diaV.findViewById(R.id.addBnt);
            final EditText title = (EditText) diaV.findViewById(R.id.todoName);
            final DatePicker day = (DatePicker) diaV.findViewById(R.id.date);
            final TimePicker time = (TimePicker) diaV.findViewById(R.id.time);
            final CheckBox c1 = (CheckBox) diaV.findViewById(R.id.togoAlarm);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "추가되었습니다", Toast.LENGTH_SHORT).show();
                    String date = day.getYear() + "-" + (int)(day.getMonth()+1) + "-" + day.getDayOfMonth();
                    String t = time.getHour() + ":" + time.getMinute();
                    String name = title.getText().toString();
                    Boolean check = c1.isChecked();
                    int id =day.getYear()*10+day.getMonth()+day.getDayOfMonth()*9+day.getId();
                    toDo item =new toDo(name, date, t, false, check,id);
                    todo.add(item);
                    db.inserttoDo(item);
                    if (check) {
                        alarm.Alarm(day.getMonth(), day.getDayOfMonth(), time.getHour(), time.getMinute(), name, todo.get(todo.size()-1).id);
                    }
                    adapter.notifyDataSetChanged();
                    mPopupDlg.dismiss();

                }
            });
        } else if (v.getId() == R.id.togoTimer) {
            Intent intent = new Intent(this, FomodoroActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.togoAlarm) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.togoSche) {
            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.auth) {


            if(!mEvernoteSession.isLoggedIn()){
                mEvernoteSession.authenticate(this);
                return ;
            }
            editor.putBoolean("init", true);
            editor.commit();

            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdfNow.format(date);
            String Etext = "";
            for (int i = 0; i < todo.size(); i++) {
                Etext += "<div>"+todo.get(i).getTitle() + " / " + todo.get(i).getTime() + " / " + todo.get(i).getCreateDate() + "</div>";

            }
            Note note = new Note();
            note.setTitle(formatDate.substring(0, 10));
            note.setContent(EvernoteUtil.NOTE_PREFIX + Etext + EvernoteUtil.NOTE_SUFFIX);
            noteStoreClient.createNoteAsync(note, new EvernoteCallback<Note>() {
                @Override
                public void onSuccess(Note result) {
                    Toast.makeText(getApplicationContext(), result.getTitle() + "이름으로 동기화되었습니다.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onException(Exception exception) {
                }
            });
            //백업 코드
        }
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

        public void Alarm(int month, int day, int h, int m, String title, int id) {
            am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(context, BroadcastD.class);
            intent.putExtra("title", title);
            sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            calendar = Calendar.getInstance();
            Log.d("gettime",month+"/"+day+"/"+calendar.getTimeInMillis()+"");

            calendar.set(calendar.get(Calendar.YEAR), month, day, h, m, 0);//time set
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }

        public void cancel(int id) {
            if (sender != null) {
                am = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(MainActivity.this, BroadcastD.class);
                sender = PendingIntent.getBroadcast(context.getApplicationContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                am.cancel(sender);
                sender.cancel();
                sender = null;
                am = null;
            }
        }
    }
}