package com.example.near.domain.shared.usecase

import com.example.near.data.storage.SettingsDataStorage
import com.example.near.domain.shared.models.ThemeType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val settingsDataStorage: SettingsDataStorage,
) {
    fun getTheme(): Flow<ThemeType> = settingsDataStorage.currentThemeFlow
}