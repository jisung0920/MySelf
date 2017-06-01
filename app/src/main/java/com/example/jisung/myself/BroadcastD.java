package com.example.jisung.myself;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by jisung on 2017-05-23.
 */

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        int timer = bundle.getInt("check");
        Toast.makeText(context, "" + timer, Toast.LENGTH_SHORT).show();
        if (timer == 1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AlarmActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.settingicon).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("아침").setContentText("설정")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent).setAutoCancel(true);
            notificationManager.notify(1, builder.build());
        }
        else if(timer==2){
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AlarmActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.settingicon).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("저녁").setContentText("설정")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent).setAutoCancel(true);
            notificationManager.notify(1, builder.build());

        }
    }
}
