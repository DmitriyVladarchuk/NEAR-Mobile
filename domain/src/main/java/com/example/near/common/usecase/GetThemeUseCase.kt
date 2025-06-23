package com.example.near.domain.shared.usecase

import com.example.near.domain.shared.storage.SettingsDataStorage

class GetThemeUseCase(
    private val settingsDataStorage: SettingsDataStorage
) {
    operator fun invoke() = settingsDataStorage.getTheme()
}