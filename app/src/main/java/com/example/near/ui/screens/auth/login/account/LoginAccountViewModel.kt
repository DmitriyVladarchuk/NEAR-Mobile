package com.example.near.ui.screens.auth.login.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginAccountViewModel() : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
}