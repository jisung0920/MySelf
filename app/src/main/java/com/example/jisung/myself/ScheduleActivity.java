package com.example.jisung.myself;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    ListView list;
    ListView[] daylist = new ListView[5];
    ArrayList<Schedule>[] schData = new ArrayList[6];
    schAdapter[] adapter = new schAdapter[5];
    SchAdapterMain adapterMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        list = (ListView)findViewById(R.id.list);

        for(int i=0;i<6;i++)
            schData[i] = new ArrayList<Schedule>();

        final int i=5;

        schData[i].add(new Schedule("0900-1030","DB","afsd",1));
        adapterMain = new SchAdapterMain(this,schData[i]);
        list.setAdapter(adapterMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ScheduleActivity.this, schData[i].get(position).getLocate(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onClick(View v){

        final View V = View.inflate(this,R.layout.time_table,null);
        if(v.getId()==R.id.addBtn){

        }
        else if(v.getId()==R.id.insertBtn){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            daylist[0] = (ListView)V.findViewById(R.id.list0);
            daylist[1] = (ListView)V.findViewById(R.id.list1);
            daylist[2] = (ListView)V.findViewById(R.id.list2);
            daylist[3] = (ListView)V.findViewById(R.id.list3);
            daylist[4] = (ListView)V.findViewById(R.id.list4);
            final TextView t1,t2,t3,t4,t5;
            t1 = (TextView)V.findViewById(R.id.t1);
            t2 = (TextView)V.findViewById(R.id.t2);
            t3 = (TextView)V.findViewById(R.id.t3);
            t4 = (TextView)V.findViewById(R.id.t4);
            t5 = (TextView)V.findViewById(R.id.t5);
            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View addV = View.inflate(V.getContext(),R.layout.add_timetable,null);
                    TextView day = (TextView)addV.findViewById(R.id.day);
                    day.setText(t1.getText().toString());
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                    final EditText time,subject,locate;
                    Button s_add;
                    time = (EditText)addV.findViewById(R.id.s_time);
                    subject = (EditText)addV.findViewById(R.id.s_subject);
                    locate = (EditText)addV.findViewById(R.id.s_locate);
                    s_add = (Button)addV.findViewById(R.id.s_add);
                    final DialogInterface exit = dialog2.setView(addV).show();
                    s_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScheduleActivity.this, "눌림", Toast.LENGTH_SHORT).show();
                            schData[0].add(new Schedule(time.getText().toString(),subject.getText().toString(),locate.getText().toString(),1));
                            exit.dismiss();
                            adapter[0].notifyDataSetChanged();
                        }
                    });

                }
            });
            t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View addV = View.inflate(V.getContext(),R.layout.add_timetable,null);
                    TextView day = (TextView)addV.findViewById(R.id.day);
                    day.setText(t2.getText().toString());
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                    final EditText time,subject,locate;
                    Button s_add;
                    time = (EditText)addV.findViewById(R.id.s_time);
                    subject = (EditText)addV.findViewById(R.id.s_subject);
                    locate = (EditText)addV.findViewById(R.id.s_locate);
                    s_add = (Button)addV.findViewById(R.id.s_add);
                    final DialogInterface exit = dialog2.setView(addV).show();
                    s_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScheduleActivity.this, "눌림", Toast.LENGTH_SHORT).show();
                            schData[1].add(new Schedule(time.getText().toString(),subject.getText().toString(),locate.getText().toString(),1));
                            exit.dismiss();
                            adapter[1].notifyDataSetChanged();
                        }
                    });
                }
            });
            t3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View addV = View.inflate(V.getContext(),R.layout.add_timetable,null);
                    TextView day = (TextView)addV.findViewById(R.id.day);
                    day.setText(t3.getText().toString());
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                    final EditText time,subject,locate;
                    Button s_add;
                    time = (EditText)addV.findViewById(R.id.s_time);
                    subject = (EditText)addV.findViewById(R.id.s_subject);
                    locate = (EditText)addV.findViewById(R.id.s_locate);
                    s_add = (Button)addV.findViewById(R.id.s_add);
                    final DialogInterface exit = dialog2.setView(addV).show();
                    s_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScheduleActivity.this, "눌림", Toast.LENGTH_SHORT).show();
                            schData[2].add(new Schedule(time.getText().toString(),subject.getText().toString(),locate.getText().toString(),1));
                            exit.dismiss();
                            adapter[2].notifyDataSetChanged();
                        }
                    });
                }
            });
            t4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View addV = View.inflate(V.getContext(),R.layout.add_timetable,null);
                    TextView day = (TextView)addV.findViewById(R.id.day);
                    day.setText(t4.getText().toString());
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                    final EditText time,subject,locate;
                    Button s_add;
                    time = (EditText)addV.findViewById(R.id.s_time);
                    subject = (EditText)addV.findViewById(R.id.s_subject);
                    locate = (EditText)addV.findViewById(R.id.s_locate);
                    s_add = (Button)addV.findViewById(R.id.s_add);
                    final DialogInterface exit = dialog2.setView(addV).show();
                    s_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScheduleActivity.this, "눌림", Toast.LENGTH_SHORT).show();
                            schData[3].add(new Schedule(time.getText().toString(),subject.getText().toString(),locate.getText().toString(),1));
                            exit.dismiss();
                            adapter[3].notifyDataSetChanged();
                        }
                    });
                }
            });
            t5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View addV = View.inflate(V.getContext(),R.layout.add_timetable,null);
                    TextView day = (TextView)addV.findViewById(R.id.day);
                    day.setText(t5.getText().toString());
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                    final EditText time,subject,locate;
                    Button s_add;
                    time = (EditText)addV.findViewById(R.id.s_time);
                    subject = (EditText)addV.findViewById(R.id.s_subject);
                    locate = (EditText)addV.findViewById(R.id.s_locate);
                    s_add = (Button)addV.findViewById(R.id.s_add);
                    final DialogInterface exit = dialog2.setView(addV).show();
                    s_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ScheduleActivity.this, "눌림", Toast.LENGTH_SHORT).show();
                            schData[4].add(new Schedule(time.getText().toString(),subject.getText().toString(),locate.getText().toString(),1));
                            exit.dismiss();
                            adapter[4].notifyDataSetChanged();
                        }
                    });
                }
            });

            for(int i=0;i<5;i++){
                schData[i].add(new Schedule("0900-1030","DB","afsd",1));
                adapter[i] = new schAdapter(this,schData[i]);
                daylist[i].setAdapter(adapter[i]);

            }
            daylist[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ScheduleActivity.this, schData[0].get(position).getLocate(), Toast.LENGTH_SHORT).show();

                }
            });//4번 반복
            daylist[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ScheduleActivity.this, schData[0].get(position).getLocate(), Toast.LENGTH_SHORT).show();

                }
            });
            daylist[2].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ScheduleActivity.this, schData[0].get(position).getLocate(), Toast.LENGTH_SHORT).show();

                }
            });
            daylist[3].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ScheduleActivity.this, schData[0].get(position).getLocate(), Toast.LENGTH_SHORT).show();

                }
            });
            daylist[4].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ScheduleActivity.this, schData[0].get(position).getLocate(), Toast.LENGTH_SHORT).show();

                }
            });
            dialog.setView(V).show();

        }
        else if(v.getId()==R.id.delBtn){

        }

    }
}
