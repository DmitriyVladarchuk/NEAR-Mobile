package com.example.near.ui.screens.auth.login.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun login() {
        viewModelScope.launch {
            val request = userRepository.login(email, password)
            if (request.isSuccess)
                email = request.getOrNull()!!.accessToken
        }
    }
}