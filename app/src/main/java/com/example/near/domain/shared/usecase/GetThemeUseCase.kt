package com.example.near.domain.shared.usecase

import com.example.near.domain.shared.storage.SettingsDataStorage
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val settingsDataStorage: SettingsDataStorage
) {
    operator fun invoke() = settingsDataStorage.getTheme()
}