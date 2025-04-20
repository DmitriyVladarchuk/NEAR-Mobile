package com.example.near.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.User
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.LogOutUseCase
import com.example.near.domain.usecase.SendFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
): ViewModel() {
    val avatarUrl: String = "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/4adf61aa-3cb7-4381-9245-523971e5b4c8/300x450"
    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadUser() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                user = getUser()
                Log.d("Test", user.toString())
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadUser(userId: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                user = getUserByIdUseCase(userId)
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun friendshipRequest(userId: String) {
        viewModelScope.launch {
            sendFriendRequestUseCase(userId)
        }
    }

    fun logOut() {
        logOutUseCase()
    }
}