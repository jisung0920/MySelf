package com.example.jisung.myself;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class removeActivity extends AppCompatActivity {

    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.song);
        mediaPlayer.start();
        Button.OnClickListener bClickListner = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.removeAlarm:
                        mediaPlayer.stop();
                        removeAlarm();
                        break;
                }
            }
        };
        findViewById(R.id.removeAlarm).setOnClickListener(bClickListner);
    }
    void removeAlarm(){
        intent = new Intent("AlarmReceiver");
        ServicePending = PendingIntent.getBroadcast(removeActivity.this,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Toast.makeText(getBaseContext(), "알람해제", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(ServicePending);
    }
}
