package com.example.near.service
import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Build
import android.app.NotificationChannel
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.near.R

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // --- Отправляем токен на сервер ---
        sendTokenToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // --- Обработка уведомления ---
        message.notification?.let { notification ->
            sendNotification(notification.title ?: "", notification.body ?: "")
        }
    }

    private fun sendNotification(title: String, body: String) {
        val channelId = "default_channel_id"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // --- Создаем канал для Android 8.0+ ---
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // --- Создаем уведомление ---
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun sendTokenToServer(token: String) {
        // --- Отправляем токен на сервер ---
        Log.d("Token", token)
    }
}