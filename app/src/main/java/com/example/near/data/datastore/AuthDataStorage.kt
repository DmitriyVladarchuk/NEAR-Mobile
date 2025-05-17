package com.example.near.data.datastore

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class AuthDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    fun saveCredentials(refreshToken: String, isCommunity: Boolean) {
        with(sharedPrefs.edit()) {
            putString("refresh_token", refreshToken)
            putBoolean("is_community", isCommunity)
            apply()
        }
    }

    fun getCredentials(): Pair<String, Boolean>? {
        val refreshToken = sharedPrefs.getString("refresh_token", null)
        val isCommunity = sharedPrefs.getBoolean("is_community", false)
        return if (refreshToken != null) Pair(refreshToken, isCommunity) else null
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

    fun clearFcmToken() {
        sharedPrefs.edit {
            remove("fcm_token")
            apply()
        }
    }
}