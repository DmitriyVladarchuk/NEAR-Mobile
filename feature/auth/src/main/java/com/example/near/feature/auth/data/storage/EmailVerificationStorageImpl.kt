package com.example.near.feature.auth.data.storage

import android.content.Context
import androidx.core.content.edit
import com.example.near.feature.auth.domain.model.LoginCredentials
import com.example.near.feature.auth.domain.storage.EmailVerificationStorage

class EmailVerificationStorageImpl(
    private val context: Context
) : EmailVerificationStorage {

    private val sharedPrefs by lazy {
        context.getSharedPreferences("email_verification_prefs", Context.MODE_PRIVATE)
    }

    override suspend fun savePendingEmail(email: String, password: String, isCommunity: Boolean) {
        sharedPrefs.edit {
            putString("pending_email", email)
            putString("pending_password", password)
            putBoolean("is_community", isCommunity)
        }
    }

    override suspend fun getPendingEmail(): LoginCredentials? {
        val email = sharedPrefs.getString("pending_email", null)
        val password = sharedPrefs.getString("pending_password", null)
        val isCommunity = sharedPrefs.getBoolean("is_community", false)
        return if (email != null && password != null ) {
            LoginCredentials(
                email = email,
                password = password,
                isCommunity = isCommunity
            )
        } else {
            null
        }
    }

    override suspend fun clearPendingEmail() {
        sharedPrefs.edit {
            remove("pending_email")
            remove("pending_password")
        }
    }
}