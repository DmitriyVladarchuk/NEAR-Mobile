package com.example.near.ui.screens.auth.signup.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.NotificationType
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.SignUpUserUseCase
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

    val selectedNotifications = mutableStateOf(setOf<NotificationType>())

    fun toggleNotification(type: NotificationType) {
        selectedNotifications.value = if (selectedNotifications.value.contains(type)) {
            selectedNotifications.value - type
        } else {
            selectedNotifications.value + type
        }
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            signUpUserUseCase(nameUser, email, password, country, birthday, listOf())
        }
    }
}