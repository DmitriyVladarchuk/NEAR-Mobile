package com.example.near.domain.shared.usecase

import com.example.near.domain.shared.models.ThemeType
import com.example.near.domain.shared.storage.SettingsDataStorage

class SetThemeUseCase(
    private val settingsDataStorage: SettingsDataStorage,
) {
    suspend operator fun invoke(theme: ThemeType): Result<Unit> = settingsDataStorage.saveTheme(theme)
}