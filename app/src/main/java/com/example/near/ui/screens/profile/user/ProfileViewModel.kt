package com.example.near.ui.screens.profile.user

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.user.User
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.user.auth.LogOutUseCase
import com.example.near.domain.usecase.user.friends.AddFriendRequestUseCase
import com.example.near.domain.usecase.user.friends.GetAllFriendsInfoUseCase
import com.example.near.domain.usecase.user.friends.RejectFriendRequestUseCase
import com.example.near.domain.usecase.user.friends.RemoveFriendUseCase
import com.example.near.domain.usecase.user.friends.SendFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FriendshipStatus {
    NOT_FRIENDS,
    REQUEST_SENT,
    REQUEST_RECEIVED,
    FRIENDS
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getAllFriendsInfoUseCase: GetAllFriendsInfoUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val addFriendRequestUseCase: AddFriendRequestUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase,
): ViewModel() {
    val avatarUrl: String = ""
    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var friendshipStatus by mutableStateOf(FriendshipStatus.NOT_FRIENDS)
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
                checkFriendshipStatus(userId)
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun checkFriendshipStatus(userId: String) {
        val currentUser = getUser()
        val allFriendsInfo = getAllFriendsInfoUseCase().getOrThrow()

        friendshipStatus = when {
            currentUser!!.friends.any { it.id == userId } -> FriendshipStatus.FRIENDS
            allFriendsInfo.sentRequests.any { it.id == userId } -> FriendshipStatus.REQUEST_SENT
            allFriendsInfo.receivedRequests.any { it.id == userId } -> FriendshipStatus.REQUEST_RECEIVED
            else -> FriendshipStatus.NOT_FRIENDS
        }
    }

    fun handleFriendshipAction(userId: String) {
        viewModelScope.launch {
            when (friendshipStatus) {
                FriendshipStatus.NOT_FRIENDS -> {
                    sendFriendRequest(userId)
                }
                FriendshipStatus.FRIENDS -> {
                    removeFriend(userId)
                }
                else -> {}
            }
        }
    }

    private suspend fun sendFriendRequest(userId: String) {
        sendFriendRequestUseCase(userId).onSuccess {
            friendshipStatus = FriendshipStatus.REQUEST_SENT
        }.onFailure {
            error = it.message ?: "Failed to send friend request"
        }
    }

    fun addFriend(userId: String) {
        viewModelScope.launch {
            addFriendRequestUseCase(userId).onSuccess {
                friendshipStatus = FriendshipStatus.FRIENDS
            }.onFailure {
                error = it.message ?: "Failed to accept friend request"
            }
        }
    }

    fun rejectFriend(userId: String) {
        viewModelScope.launch {
            rejectFriendRequestUseCase(userId).onSuccess {
                friendshipStatus = FriendshipStatus.NOT_FRIENDS
            }.onFailure {
                error = it.message ?: "Failed to reject friend request"
            }
        }
    }

    private suspend fun removeFriend(userId: String) {
        removeFriendUseCase(userId).onSuccess {
            friendshipStatus = FriendshipStatus.NOT_FRIENDS
        }.onFailure {
            error = it.message ?: "Failed to remove friend"
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
            _logoutEvent.emit(Unit)
        }
    }
}