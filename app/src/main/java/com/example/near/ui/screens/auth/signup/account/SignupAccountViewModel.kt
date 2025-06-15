package com.example.near.ui.screens.auth.signup.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.common.SignupNotificationOption
import com.example.near.domain.usecase.user.auth.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupAccountViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel() {
    var nameUser by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var country by mutableStateOf("")
    var birthday by mutableStateOf("")
    var phone by mutableStateOf("")
    var telegramShortName by mutableStateOf("")

    val selectedNotifications = mutableStateOf(setOf<SignupNotificationOption>())

    fun toggleNotification(option: SignupNotificationOption) {
        selectedNotifications.value = if (selectedNotifications.value.contains(option)) {
            selectedNotifications.value - option
        } else {
            selectedNotifications.value + option
        }
    }

    fun onSignUpClick(navigateToDashboards: () -> Unit) {
        viewModelScope.launch {
            if (signUpUserUseCase(nameUser, email, password, country, birthday, phone, telegramShortName, selectedNotifications.value.toList()).isSuccess) {
                navigateToDashboards()
            }
        }
    }
}