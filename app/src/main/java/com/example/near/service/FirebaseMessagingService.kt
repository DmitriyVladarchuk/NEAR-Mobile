package com.example.near.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.near.R
import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyFirebaseMessagingService @Inject constructor(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage
) : FirebaseMessagingService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        scope.launch {
            handleNewToken(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
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

    private suspend fun handleNewToken(token: String) {
        Log.d("FCM", "New token received: $token")

        if (sessionManager.isLoggedIn) {
            sendTokenForCurrentUser(token)
        } else {
            authDataStorage.saveFcmToken(token)
        }
    }

    private suspend fun sendTokenForCurrentUser(token: String) {
        try {
            val credentials = authDataStorage.getCredentials()
            when {
                credentials?.second == true -> { // Это сообщество
                    communityRepository.sendFcmToken(token).onSuccess {
                        Log.d("FCM", "Community token sent successfully")
                    }.onFailure { e ->
                        Log.e("FCM", "Failed to send community token", e)
                    }
                }
                credentials != null -> { // Это обычный пользователь
                    userRepository.sendFcmToken(token).onSuccess {
                        Log.d("FCM", "User token sent successfully")
                    }.onFailure { e ->
                        Log.e("FCM", "Failed to send user token", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("FCM", "Error sending token", e)
        }
    }
}