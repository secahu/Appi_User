package com.gesdes.android.conductor.appi_user

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.iid.FirebaseInstanceId
import android.util.Log
import androidx.core.app.NotificationCompat


class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(message.notification!!.title)
            .setContentText(message.notification!!.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }

    private val LOGTAG = "android-fcm"

    fun onTokenRefresh() {
        //Se obtiene el token actualizado
        val refreshedToken = FirebaseInstanceId.getInstance().token

        Log.d(LOGTAG, "Token actualizado: " + refreshedToken!!)
    }

}