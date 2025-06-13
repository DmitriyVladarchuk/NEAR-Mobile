package com.example.near.domain.usecase

import com.example.near.data.datastore.SettingsDataStorage
import com.example.near.domain.models.common.ThemeType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val settingsDataStorage: SettingsDataStorage,
) {
    fun getTheme(): Flow<ThemeType> = settingsDataStorage.currentThemeFlow
}