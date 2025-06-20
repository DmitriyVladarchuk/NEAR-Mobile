package com.example.near.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.shared.models.ThemeType
import com.example.near.domain.shared.usecase.GetThemeUseCase
import com.example.near.domain.shared.usecase.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
): ViewModel() {
    val currentTheme: Flow<ThemeType> = getThemeUseCase.getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThemeType.SYSTEM
    )

    fun setTheme(theme: ThemeType) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }
}