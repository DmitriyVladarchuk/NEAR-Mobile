package com.example.near.domain.usecase

import com.example.near.data.storage.SettingsDataStorage
import com.example.near.domain.models.common.ThemeType
import javax.inject.Inject

class SetThemeUseCase  @Inject constructor(
    private val settingsDataStorage: SettingsDataStorage,
) {
    suspend operator fun invoke(theme: ThemeType) {
        settingsDataStorage.saveTheme(theme)
    }
}