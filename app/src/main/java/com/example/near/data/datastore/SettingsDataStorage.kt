package com.example.near.data.datastore

import android.content.Context
import com.example.near.domain.models.ThemeType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.core.content.edit

class SettingsDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    }

    private val _currentThemeFlow = MutableStateFlow(getTheme())
    val currentThemeFlow: Flow<ThemeType> = _currentThemeFlow

    fun saveTheme(theme: ThemeType) {
        sharedPrefs.edit() {
            putString("app_theme", theme.name)
        }
        _currentThemeFlow.value = theme
    }

    fun getTheme(): ThemeType {
        val savedTheme = sharedPrefs.getString("app_theme", ThemeType.SYSTEM.name)
        return ThemeType.valueOf(savedTheme ?: ThemeType.SYSTEM.name)
    }
}