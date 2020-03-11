package com.gesdes.android.conductor.appi_user

import android.R
import android.app.NotificationManager
import android.media.RingtoneManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.content.Context;
import androidx.core.app.NotificationCompat;

class MyFirebaseMessagingService: FirebaseMessagingService() {




    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(message.notification!!.title)
                .setContentText(message.notification!!.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setAutoCancel(true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}