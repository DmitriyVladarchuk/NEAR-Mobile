package com.example.near.ui.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.shared.models.ThemeType
import com.example.near.domain.shared.usecase.GetThemeUseCase
import com.example.near.domain.shared.usecase.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel() {

    private var _currentTheme = mutableStateOf(getThemeUseCase())
    val currentTheme: State<ThemeType> = _currentTheme

    fun setTheme(theme: ThemeType) {
        viewModelScope.launch {
            setThemeUseCase(theme)
                .onSuccess {
                    _currentTheme.value= theme
                }
        }
        _currentTheme.value = theme
        //_currentTheme = getThemeUseCase()
    }
}