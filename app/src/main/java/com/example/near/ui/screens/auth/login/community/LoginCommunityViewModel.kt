package com.example.near.ui.screens.auth.login.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginCommunityViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
}