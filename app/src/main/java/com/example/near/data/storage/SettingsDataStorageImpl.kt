package com.example.near.data.storage

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.near.domain.shared.models.ThemeType
import com.example.near.domain.shared.storage.SettingsDataStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsDataStorage {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    }

    override fun saveTheme(theme: ThemeType): Result<Unit> {
        return try {
            sharedPrefs.edit {
                putString("app_theme", theme.name)
            }
            applyTheme(theme)
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }

    }

    override fun getTheme(): ThemeType {
        val savedTheme = sharedPrefs.getString("app_theme", ThemeType.SYSTEM.name)
        return ThemeType.valueOf(savedTheme ?: ThemeType.SYSTEM.name)
    }

    private fun applyTheme(theme: ThemeType) {
        val mode = when (theme) {
            ThemeType.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeType.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeType.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    init {
        applyTheme(getTheme())
    }
}