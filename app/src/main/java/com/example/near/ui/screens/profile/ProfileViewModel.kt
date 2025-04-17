package com.example.near.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.User
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase
): ViewModel() {
    val avatarUrl: String = "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/4adf61aa-3cb7-4381-9245-523971e5b4c8/300x450"
    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                user = getUser()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun logOut() {
        logOutUseCase()
    }
}