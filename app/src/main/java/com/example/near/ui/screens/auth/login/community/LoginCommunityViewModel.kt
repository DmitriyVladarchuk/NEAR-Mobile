package com.example.near.ui.screens.auth.login.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.usecase.community.LoginCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginCommunityViewModel @Inject constructor(
    private val loginCommunityUseCase: LoginCommunityUseCase
): ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun login(navigateToDashboards: () -> Unit) {
        viewModelScope.launch {
            val request = loginCommunityUseCase(email, password)
            if (request.isSuccess) {
                navigateToDashboards()
            }
        }
    }
}