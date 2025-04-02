package com.example.near.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.near.domain.models.NotificationType

class LoginAccountViewModel : ViewModel() {
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

    }
}