package com.example.jisung.myself;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class FomodoroActivity extends AppCompatActivity {

    TextView today,countT,turnT;

    int Smin=25;
    int Ssec=0;
    private int min = Smin;
    private int sec = Ssec;
    private int Rmin=Smin;
    private int Rsec=Ssec;

    int MILLISINFUTURE = min*60000+sec*1000;
    int COUNT_DOWN_INTERVAL = 1000;


    private TextView countTxt ;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomodoro);
        init();


    }

    void init(){
        today = (TextView)findViewById(R.id.today);
        countT = (TextView)findViewById(R.id.countText);
        turnT = (TextView)findViewById(R.id.turnText);
        countTxt = (TextView)findViewById(R.id.timer);

        //timer.setFormat("25:00");
    }

    public void countDownTimer(int i,int j){

        countDownTimer = new CountDownTimer(i, j) {
            public void onTick(long millisUntilFinished) {
                String time ="";
                if(min>0)
                    time= min+":";
                if(sec<10)
                    time +="0"+sec;
                else
                    time +=sec;
                if(sec!=0){
                    --sec;
                }
                else{
                    min--;
                    sec = 59;
                }
                countTxt.setText(time);
            }
            public void onFinish() {
                if (Rmin == 25) {
                    min = 5;
                    sec = 0;
                    Rmin =Smin = min;
                    Rsec =Ssec = sec;
                    MILLISINFUTURE = min * 60000 + sec * 1000;
                    COUNT_DOWN_INTERVAL = 1000;
                    countTxt.setText(String.valueOf("05:00."));
                }
                else{
                    min = 25;
                    sec = 0;
                    Smin = min;
                    Ssec = sec;
                    MILLISINFUTURE = min * 60000 + sec * 1000;
                    COUNT_DOWN_INTERVAL = 1000;
                    countTxt.setText(String.valueOf("25:00."));

                }
            }
        };
    }

    public void onClick(View v){
        if(v.getId() == R.id.countBtn){
            countDownTimer(MILLISINFUTURE,COUNT_DOWN_INTERVAL);
            min = Smin;
            sec = Ssec;
            countDownTimer.start();

        }
        else if(v.getId()==R.id.stopBtn){
            countDownTimer.cancel();
            Smin = min ;
            Ssec = sec;
            MILLISINFUTURE = min*60000+sec*1000;
            COUNT_DOWN_INTERVAL = 1000;

        }
        else if(v.getId()==R.id.resetBtn){
            countDownTimer.cancel();
            countTxt.setText(Rmin+":00");
            min = Rmin;
            sec = Rsec;

        }

    }

}
