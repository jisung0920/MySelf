package com.example.jisung.myself;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    TextView today;
    ListView list;
    ListView[] daylist = new ListView[5];
    ArrayList<Schedule>[] schData = new ArrayList[6];
    schAdapter[] adapter = new schAdapter[5];
    SchAdapterMain adapterMain;
    ImageButton delBtn;
    Boolean delCount = false,e = true;
    View V;
    TextView t1, t2, t3, t4, t5;
    int todayL=5;

    int dateSetting(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdfNow.format(date);
        String day ="";
        try {
            today.setText(formatDate.substring(0, 4) + "년 " + formatDate.substring(5, 7) + "월 " + formatDate.substring(8, 10) + "일  "+getDateDay(formatDate,"yyyy/MM/dd")+"요일");
            day = getDateDay(formatDate,"yyyy/MM/dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result=5;
        switch (day) {
            case "월":
                result=0;
                break;
            case "화":
                result=1;
                break;
            case "수":
                result=2;
                break;
            case "목":
                result=3;
                break;
            case "금":
                result=4;
                break;
            default:
                result=5;
        }
        return result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        init();
        lordObject();

        adapterMain = new SchAdapterMain(this, schData[todayL]);
        list.setAdapter(adapterMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ScheduleActivity.this, schData[todayL].get(position).getLocate(), Toast.LENGTH_SHORT).show();

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (delCount)
                    schData[todayL].remove(position);
                adapterMain.notifyDataSetChanged();
                return true;
            }
        });


    }

    void lordObject(){
        try {
            for (int l = 0; l <schData.length; l++) {

                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilesDir()+"SCD"+l+".ser"));
                    schData[l] = (ArrayList<Schedule>) ois.readObject();
                    ois.close();
                    Log.d("listdata", "" +l);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<adapter.length;i++){
            adapter[i]=new schAdapter(this,schData[i]);
            daylist[i].setAdapter(adapter[i]);
        }
    }


    void init() {
        list = (ListView) findViewById(R.id.list);
        today = (TextView) findViewById(R.id.today);
        delBtn = (ImageButton) findViewById(R.id.delBtn);
        V = View.inflate(this, R.layout.time_table, null);
        daylist[0] = (ListView) V.findViewById(R.id.list0);
        daylist[1] = (ListView) V.findViewById(R.id.list1);
        daylist[2] = (ListView) V.findViewById(R.id.list2);
        daylist[3] = (ListView) V.findViewById(R.id.list3);
        daylist[4] = (ListView) V.findViewById(R.id.list4);
        t1 = (TextView) V.findViewById(R.id.t1);
        t2 = (TextView) V.findViewById(R.id.t2);
        t3 = (TextView) V.findViewById(R.id.t3);
        t4 = (TextView) V.findViewById(R.id.t4);
        t5 = (TextView) V.findViewById(R.id.t5);
        todayL = dateSetting();
        for (int i = 0; i < 6; i++)
            schData[i] = new ArrayList<Schedule>();
    }

    public void onClick(View v) {


        if (v.getId() == R.id.addBtn) {

            final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
            TextView day = (TextView) addV.findViewById(R.id.day);
            final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
            final EditText time, subject, locate;
            Button s_add;
            day.setText("");
            time = (EditText) addV.findViewById(R.id.s_time);
            subject = (EditText) addV.findViewById(R.id.s_subject);
            locate = (EditText) addV.findViewById(R.id.s_locate);
            s_add = (Button) addV.findViewById(R.id.s_add);
            final DialogInterface exit = dialog2.setView(addV).show();
            s_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                    schData[todayL].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                    exit.dismiss();
                    adapterMain.notifyDataSetChanged();
                }
            });

        } else if (v.getId() == R.id.insertBtn) {
            if(e) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                t1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
                        TextView day = (TextView) addV.findViewById(R.id.day);
                        day.setText(t1.getText().toString());
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                        final EditText time, subject, locate;
                        Button s_add;
                        time = (EditText) addV.findViewById(R.id.s_time);
                        subject = (EditText) addV.findViewById(R.id.s_subject);
                        locate = (EditText) addV.findViewById(R.id.s_locate);
                        s_add = (Button) addV.findViewById(R.id.s_add);
                        final DialogInterface exit = dialog2.setView(addV).show();
                        s_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                                schData[0].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                                exit.dismiss();
                                adapter[0].notifyDataSetChanged();
                            }
                        });

                    }
                });
                t2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
                        TextView day = (TextView) addV.findViewById(R.id.day);
                        day.setText(t2.getText().toString());
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                        final EditText time, subject, locate;
                        Button s_add;
                        time = (EditText) addV.findViewById(R.id.s_time);
                        subject = (EditText) addV.findViewById(R.id.s_subject);
                        locate = (EditText) addV.findViewById(R.id.s_locate);
                        s_add = (Button) addV.findViewById(R.id.s_add);
                        final DialogInterface exit = dialog2.setView(addV).show();
                        s_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                                schData[1].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                                exit.dismiss();
                                adapter[1].notifyDataSetChanged();
                            }
                        });
                    }
                });
                t3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
                        TextView day = (TextView) addV.findViewById(R.id.day);
                        day.setText(t3.getText().toString());
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                        final EditText time, subject, locate;
                        Button s_add;
                        time = (EditText) addV.findViewById(R.id.s_time);
                        subject = (EditText) addV.findViewById(R.id.s_subject);
                        locate = (EditText) addV.findViewById(R.id.s_locate);
                        s_add = (Button) addV.findViewById(R.id.s_add);
                        final DialogInterface exit = dialog2.setView(addV).show();
                        s_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                                schData[2].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                                exit.dismiss();
                                adapter[2].notifyDataSetChanged();
                            }
                        });
                    }
                });
                t4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
                        TextView day = (TextView) addV.findViewById(R.id.day);
                        day.setText(t4.getText().toString());
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                        final EditText time, subject, locate;
                        Button s_add;
                        time = (EditText) addV.findViewById(R.id.s_time);
                        subject = (EditText) addV.findViewById(R.id.s_subject);
                        locate = (EditText) addV.findViewById(R.id.s_locate);
                        s_add = (Button) addV.findViewById(R.id.s_add);
                        final DialogInterface exit = dialog2.setView(addV).show();
                        s_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                                schData[3].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                                exit.dismiss();
                                adapter[3].notifyDataSetChanged();
                            }
                        });
                    }
                });
                t5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View addV = View.inflate(V.getContext(), R.layout.add_timetable, null);
                        TextView day = (TextView) addV.findViewById(R.id.day);
                        day.setText(t5.getText().toString());
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(V.getContext());
                        final EditText time, subject, locate;
                        Button s_add;
                        time = (EditText) addV.findViewById(R.id.s_time);
                        subject = (EditText) addV.findViewById(R.id.s_subject);
                        locate = (EditText) addV.findViewById(R.id.s_locate);
                        s_add = (Button) addV.findViewById(R.id.s_add);
                        final DialogInterface exit = dialog2.setView(addV).show();
                        s_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScheduleActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                                schData[4].add(new Schedule(time.getText().toString(), subject.getText().toString(), locate.getText().toString(), 1));
                                exit.dismiss();
                                adapter[4].notifyDataSetChanged();
                            }
                        });
                    }
                });


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
                e=false;
            }

        } else if (v.getId() == R.id.delBtn) {
            if (delCount) {
                delBtn.setImageResource(R.drawable.deleteicon);
                delCount = false;
            } else {
                delBtn.setImageResource(R.drawable.deletelist);
                delCount = true;
                Toast.makeText(this, "길게 누르면 삭제가 됩니다.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("listdata", "data save");
        try {
            for (int l = 0; l < schData.length; l++) {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilesDir()+"SCD"+l+".ser",false));
                    oos.writeObject(schData[l]);
                    oos.close();
                    Log.d("listdata", l + "save" );
                }

        } catch (Exception e) {
            // TODO: handle exception
            Log.d("listdata","error");
            e.printStackTrace();
        }
    }

    public static String getDateDay(String date, String dateType) throws Exception {
        String day = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Date nDate = dateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }
        return day;
    }

}
