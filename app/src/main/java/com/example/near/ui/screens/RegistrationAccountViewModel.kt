package com.example.near.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistrationAccountViewModel : ViewModel() {

    var nameUser by mutableStateOf("")

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var country by mutableStateOf("")

    var birthday by mutableStateOf("")

    var pushNotification = mutableStateListOf<String>()

}