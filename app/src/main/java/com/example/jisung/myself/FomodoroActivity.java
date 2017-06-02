package com.example.jisung.myself;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FomodoroActivity extends AppCompatActivity {

    TextView today, countT, turnT,totalText;
    Button setting;
    Vibrator vide;
    MyTask task1;
    int total=0;
    SharedPreferences tmp;
    SharedPreferences.Editor editor;
    int min = 25,sec = 0,res = 5,longrest=10;
    int resS = 0;
    int tmpM, tmpS, count = 0, turn = 0, m = 4,nowM=0;

    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomodoro);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tmp = getSharedPreferences("test", MODE_PRIVATE);
        editor = tmp.edit();
        init();
        setting.callOnClick();
        task1 = new MyTask();




    }

    void init() {
        today = (TextView) findViewById(R.id.today);
        countT = (TextView) findViewById(R.id.countText);
        turnT = (TextView) findViewById(R.id.turnText);
        t1 = (TextView) findViewById(R.id.timer);
        vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setting = (Button)findViewById(R.id.setBtn);
        totalText = (TextView)findViewById(R.id.total);
        total = tmp.getInt("total",0);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdfNow.format(date);
        today.setText(formatDate.substring(0,4)+"년 "+formatDate.substring(5,7)+"월 "+formatDate.substring(8,10)+"일");
        totalText.setText(total/60+"분");
    }



    private class MyTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {//-2,4
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < 4; k++) {
                    publishProgress(-2);
                    for (int i = min * 60 + sec - 1; i > 0; i--) {
                        if (isCancelled() == true) return null;
                        try {
                            tmpM = i / 60;
                            tmpS = i % 60;
                            Thread.sleep(1000);
                            publishProgress(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    vide.vibrate(3000);
                    count++;
                    publishProgress(-1);
                    for (int i = res * 60 + resS - 1; i > 0; i--) {
                        if (isCancelled() == true) return null;
                        try {
                            tmpM = i / 60;
                            tmpS = i % 60;
                            Thread.sleep(1000);
                            publishProgress(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    vide.vibrate(3000);
                }
                publishProgress(-3);
                for (int i = longrest*60; i > 0; i--) {
                    if (isCancelled() == true) return null;
                    try {
                        tmpM = i / 60;
                        tmpS = i % 60;
                        Thread.sleep(1000);
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                vide.vibrate(3000);
                turn++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0]==-2)
                t1.setTextColor(Color.BLACK);
            else if(values[0]==-1)
                t1.setTextColor(Color.GREEN);
            else if(values[0]==-3)
                t1.setTextColor(Color.BLUE);
            else if ((values[0]/60)>0 && (values[0] % 60 < 10))
                t1.setText(values[0] / 60 + ":0" + values[0] % 60);
            else if (values[0] < 60)
                t1.setText(values[0]+"");
            else
                t1.setText(values[0] / 60 + ":" + values[0] % 60);

            if(t1.getTextColors()==ColorStateList.valueOf(Color.BLACK))
                total++;
            nowM++;
            totalText.setText(total/60+"분");
            countT.setText(count+"");
            turnT.setText(turn+"");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            t1.setTextColor(Color.BLACK);
            t1.setText("");
            Toast.makeText(FomodoroActivity.this, "수고하셨습니다.", Toast.LENGTH_SHORT).show();
            vide.vibrate(1000);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            min = tmpM;
            sec = tmpS;
        }
    }


    public void onClick(View v) {
        if (v.getId() == R.id.countBtn) {
            task1 = new MyTask();
            task1.execute(0);
        } else if (v.getId() == R.id.stopBtn) {
            task1.cancel(true);
        } else if (v.getId() == R.id.resetBtn) {
            nowM = nowM-turn*res;
            int nowS = nowM%60-resS;
            Toast.makeText(this, "학습시간 : "+nowM/60+"분 "+nowS+"초", Toast.LENGTH_SHORT).show();
        } else if(v.getId()==R.id.setBtn){
            final EditText m1,m2,m3,s1,s2,e1;
            final Button b1;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            final View setV = View.inflate(this,R.layout.time_set_view,null);
            m1 = (EditText)setV.findViewById(R.id.m1);
            m2 = (EditText)setV.findViewById(R.id.m2);
            m3 = (EditText)setV.findViewById(R.id.m3);
            s1 = (EditText)setV.findViewById(R.id.s1);
            s2 = (EditText)setV.findViewById(R.id.s2);
            b1 = (Button)setV.findViewById(R.id.b1);
            e1 = (EditText)setV.findViewById(R.id.e1);
            final DialogInterface exit = dialog.setView(setV).show();
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    min=Integer.parseInt(m1.getText().toString());
                    res=Integer.parseInt(m2.getText().toString());
                    longrest=Integer.parseInt(m3.getText().toString());
                    sec=Integer.parseInt(s1.getText().toString());
                    resS=Integer.parseInt(s2.getText().toString());
                    m = Integer.parseInt(e1.getText().toString());
                    t1.setText(min + ":"+sec);
                    exit.dismiss();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt("total",total);
        editor.commit();
        if (task1.getStatus() == AsyncTask.Status.RUNNING)
        {
            task1.cancel(true);
        }
        else
        {
        }

    }
}
