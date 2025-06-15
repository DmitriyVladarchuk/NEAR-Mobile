package com.example.near.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.near.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val fcmTokenManager: FcmTokenManager by lazy {
        FcmTokenManagerHolder.getManager(applicationContext)
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        scope.launch {
            fcmTokenManager.handleNewToken(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        scope.launch {
            fcmTokenManager.saveIsPush()
        }

        message.notification?.let { notification ->
            sendNotification(notification.title ?: "", notification.body ?: "")
        }
    }

    private fun sendNotification(title: String, body: String) {
        val channelId = "default_channel_id"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }
}

object FcmTokenManagerHolder {
    private var manager: FcmTokenManager? = null

    fun init(manager: FcmTokenManager) {
        this.manager = manager
    }

    fun getManager(context: Context): FcmTokenManager {
        return manager ?: throw IllegalStateException("FcmTokenManager not initialized")
    }
}