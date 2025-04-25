package com.example.near.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.near.domain.models.User
import com.example.near.domain.usecase.AddFriendRequestUseCase
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.LogOutUseCase
import com.example.near.domain.usecase.RejectFriendRequestUseCase
import com.example.near.domain.usecase.SendFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val addFriendRequestUseCase: AddFriendRequestUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase
): ViewModel() {
    val avatarUrl: String = ""
    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

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

    fun addFriend(userId: String) {
        viewModelScope.launch {
            addFriendRequestUseCase(userId)
        }
    }

    fun rejectFriend(userId: String) {
        viewModelScope.launch {
            rejectFriendRequestUseCase(userId)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
            _logoutEvent.emit(Unit)
        }
    }
}