package com.example.jisung.myself;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    GridView list;
    todoAdapter adapter;
    ArrayList<toDo> todo;
    private static final String CONSUMER_KEY = "jisung0920-2994";
    private static final String CONSUMER_SECRET = "2fd26f8b8197436b";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;
    EvernoteSession mEvernoteSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEvernoteSession = new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(true)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();
        list = (GridView)findViewById(R.id.list);
        todo = new ArrayList<toDo>();
        todo.add(new toDo("멀미","asdf","afsd",true,false));
        todo.add(new toDo("컴구","asdf","afsd",true,false));
        todo.add(new toDo("디비","asdf","afsd",true,false));
        todo.add(new toDo("데통","asdf","afsd",true,false));


        adapter = new todoAdapter(this,todo);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, todo.get(position).getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todo.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
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
                    Toast.makeText(MainActivity.this, "추가되었습니다", Toast.LENGTH_SHORT).show();
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
        else if(v.getId()==R.id.auth){
            mEvernoteSession.authenticate(this);
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
        else if(v.getId() ==R.id.backup){

//            if (!EvernoteSession.getInstance().isLoggedIn()) {
//                return;


//            }
//
//            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
//            noteStoreClient.listNotebooksAsync(new EvernoteCallback<List<Notebook>>() {
//                @Override
//                public void onSuccess(List<Notebook> result) {
//                    List<String> namesList = new ArrayList<>(result.size());
//
//                    for (Notebook notebook : result) {
//                        namesList.add(notebook.getName());
//                        Log.d("evernote",notebook.getName());
//
//                    }
//                    String notebookNames = TextUtils.join(", ", namesList);
//                    Toast.makeText(getApplicationContext(), notebookNames + " notebooks have been retrieved", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onException(Exception exception) {
//                   // Log.e(LOGTAG, "Error retrieving notebooks", exception);
//                }
//            });
//            if (!EvernoteSession.getInstance().isLoggedIn()) {
//                return;
//            }

            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdfNow.format(date);
            String Etext="";
            for(int i=0;i<todo.size();i++)
                Etext+=todo.get(i).getTitle()+"\n";
            Note note = new Note();
            note.setTitle(formatDate.substring(0,10));
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
}
