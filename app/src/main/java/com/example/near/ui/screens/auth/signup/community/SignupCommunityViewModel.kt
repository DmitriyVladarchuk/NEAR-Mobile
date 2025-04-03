package com.example.near.ui.screens.auth.signup.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignupCommunityViewModel : ViewModel() {
    var communityName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var monitoringRegion by mutableStateOf("")
    var emergencyType by mutableStateOf("")
}