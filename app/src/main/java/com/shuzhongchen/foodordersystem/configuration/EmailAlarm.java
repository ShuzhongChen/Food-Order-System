package com.shuzhongchen.foodordersystem.configuration;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mengtongma on 5/9/18.
 */

public class EmailAlarm extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Reminder Sent", Toast.LENGTH_SHORT).show();

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Reminder!")
//                .setContentText("Your order is ready, please go and grab you food!")
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                .setContentInfo("Info");
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, builder.build());
        String userEmail = intent.getStringExtra("email");
        Log.d("Reminder", "onReceive: " + userEmail);

        String subject = "IFood Reminder";
        String message = "Your order is ready! Go grab your food!";
        SendMail sm = new SendMail(context, userEmail, subject, message);
        sm.execute();
        
    }

}
