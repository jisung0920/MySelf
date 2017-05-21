package com.example.jisung.myself;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jisung on 2017-05-21.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");
        wakeLock.acquire();
        Log.d("alram","Go");
        PendingIntent pendingIntent;
        Toast.makeText(context, "알림이 울립니다.", Toast.LENGTH_SHORT).show();
        wakeLock.release();
        try {
            intent = new Intent(context,removeActivity.class);
            pendingIntent = PendingIntent.getActivity(context,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            Log.d("servicePeding++:",""+pendingIntent.toString());
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        notification();
    }
    void notification(){
        Intent intent = new Intent();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),android.R.drawable.ic_dialog_email);
        PendingIntent pendingintent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context).setSound(soundUri).setContentTitle("알람")
                .setContentIntent(pendingintent);
        NotificationManager notificationManger = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManger.notify(0,notificationBuilder.build());
    }
}
