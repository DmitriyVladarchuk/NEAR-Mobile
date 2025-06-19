package com.example.near.data.storage

import android.content.Context
import androidx.core.content.edit
import com.example.near.domain.models.common.AuthCredentials
import com.example.near.domain.repository.AuthDataStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthDataStorage {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    override fun saveCredentials(credentials: AuthCredentials) {
        sharedPrefs.edit {
            putString("access_token", credentials.accessToken)
            putString("refresh_token", credentials.refreshToken)
            putBoolean("is_community", credentials.isCommunity)
        }
    }

    override fun getCredentials(): AuthCredentials? {
        val refreshToken = sharedPrefs.getString("refresh_token", null)
        return refreshToken?.let {
            AuthCredentials(
                accessToken = sharedPrefs.getString("access_token", "")!!,
                refreshToken = it,
                isCommunity = sharedPrefs.getBoolean("is_community", false)
            )
        }
    }

    override fun clearCredentials() {
        sharedPrefs.edit() { clear() }
    }

    // --- FCM token ---

    override fun saveFcmToken(token: String) {
        sharedPrefs.edit {
            putString("fcm_token", token)
            apply()
        }
    }

    override fun getFcmToken(): String? {
        return sharedPrefs.getString("fcm_token", null)
    }

    override fun saveIsPush() {
        sharedPrefs.edit {
            putBoolean("is_push", true)
            apply()
        }
    }

    fun getIsPush(): Boolean {
        return sharedPrefs.getBoolean("is_push", false)
    }

    override fun clearFcmToken() {
        sharedPrefs.edit {
            remove("fcm_token")
            apply()
        }
    }
}