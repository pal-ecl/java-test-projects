package com.lescure.testAndroid;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationPublisherBR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra("MaCle");

        NotificationManagerCompat ncm = NotificationManagerCompat.from(context);
        ncm.notify(29, notification);
    }
}
