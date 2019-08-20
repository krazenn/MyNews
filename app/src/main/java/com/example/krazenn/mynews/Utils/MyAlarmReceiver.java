package com.example.krazenn.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.krazenn.mynews.R;

public class MyAlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "notif";
    private static final int NOTIFICATION_ID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Titre")
                .setContentText("Contenu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // notificationId est un identificateur unique par notification qu'il vous faut définir
        notificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }
}
