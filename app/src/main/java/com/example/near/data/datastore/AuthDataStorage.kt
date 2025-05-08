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

    fun saveCredentials(email: String, password: String, isCommunity: Boolean) {
        with(sharedPrefs.edit()) {
            putString("email", email)
            putString("password", password)
            putBoolean("is_community", isCommunity)
            apply()
        }
    }

    fun getCredentials(): Triple<String, String, Boolean>? {
        val email = sharedPrefs.getString("email", null)
        val password = sharedPrefs.getString("password", null)
        val isCommunity = sharedPrefs.getBoolean("is_community", false)
        return if (email != null && password != null) Triple(email, password, isCommunity) else null
    }

    fun clearCredentials() {
        sharedPrefs.edit() { clear() }
    }
}