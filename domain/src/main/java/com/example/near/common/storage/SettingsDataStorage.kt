package com.example.near.domain.shared.storage

import com.example.near.domain.shared.models.ThemeType

interface SettingsDataStorage {

    fun saveTheme(theme: ThemeType): Result<Unit>

    fun getTheme(): ThemeType

}