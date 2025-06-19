package com.example.near.data.storage

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    fun saveCredentials(accessToken: String, refreshToken: String, isCommunity: Boolean) {
        sharedPrefs.edit {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            putBoolean("is_community", isCommunity)
            putBoolean("is_push", false)
            apply()
        }
    }

    fun getCredentials(): Triple<String, String, Boolean>? {
        val accessToken = sharedPrefs.getString("access_token", null)
        val refreshToken = sharedPrefs.getString("refresh_token", null)
        val isCommunity = sharedPrefs.getBoolean("is_community", false)
        return if (accessToken != null && refreshToken != null) Triple(accessToken, refreshToken, isCommunity) else null
    }

    fun clearCredentials() {
        sharedPrefs.edit() { clear() }
    }

    // --- FCM token ---

    fun saveFcmToken(token: String) {
        sharedPrefs.edit {
            putString("fcm_token", token)
            apply()
        }
    }

    fun getFcmToken(): String? {
        return sharedPrefs.getString("fcm_token", null)
    }

    fun saveIsPush() {
        sharedPrefs.edit {
            putBoolean("is_push", true)
            apply()
        }
    }

    fun getIsPush(): Boolean {
        return sharedPrefs.getBoolean("is_push", false)
    }

    fun clearFcmToken() {
        sharedPrefs.edit {
            remove("fcm_token")
            apply()
        }
    }
}