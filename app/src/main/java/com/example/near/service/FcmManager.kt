package com.example.near.service

import android.util.Log
import com.example.near.common.storage.AuthDataStorage
import com.example.near.data.storage.SessionManager
import com.example.near.feature.community.domain.repository.CommunityRepository
import com.example.near.feature.user.domain.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FcmTokenManager @Inject constructor(
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val sessionManager: SessionManager,
    private val authDataStorage: AuthDataStorage
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun saveIsPush() {
        scope.launch {
            authDataStorage.saveIsPush()
        }
    }

    suspend fun handleNewToken(token: String) {
        Log.d("FCM", "New token received: $token")

        if (sessionManager.isLoggedIn) {
            sendTokenForCurrentUser(token)
        } else {
            authDataStorage.saveFcmToken(token)
        }
    }

    suspend fun forceGenerateNewToken(): String? {
        return try {
            FirebaseMessaging.getInstance().deleteToken().await()
            delay(1000)
            val newToken = FirebaseMessaging.getInstance().awaitToken()
            Log.d("FCM", "Force generated new token: $newToken")
            handleNewToken(newToken)

            newToken
        } catch (e: Exception) {
            Log.e("FCM", "Error forcing token generation", e)
            null
        }
    }

    private suspend fun sendTokenForCurrentUser(token: String) {
        try {
            val credentials = authDataStorage.getCredentials()
            when {
                credentials?.isCommunity == true -> { // Это сообщество
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
            authDataStorage.saveFcmToken(token)
        } catch (e: Exception) {
            Log.e("FCM", "Error sending token", e)
        }
    }

    private suspend fun FirebaseMessaging.awaitToken(): String {
        return suspendCoroutine { continuation ->
            token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result)
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("FCM token error"))
                }
            }
        }
    }
}