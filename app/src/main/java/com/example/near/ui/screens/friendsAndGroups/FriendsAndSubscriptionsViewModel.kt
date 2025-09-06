package com.example.near.ui.screens.friendsAndGroups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.feature.user.domain.models.UserFriend
import com.example.near.feature.user.domain.models.UserSubscription
import com.example.near.feature.user.domain.usecase.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Tabs { FRIENDS, SUBSCRIPTIONS }

@HiltViewModel
class FriendsAndSubscriptionsViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var friends: List<UserFriend> by mutableStateOf(listOf())
        private set

    var subscriptions: List<UserSubscription> by mutableStateOf(listOf())
        private set

    var selectedTab by mutableStateOf(Tabs.FRIENDS)


    fun loadData(id: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val user = getUserByIdUseCase(id)
                friends = user?.friends ?: listOf()
                subscriptions = user?.subscriptions ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun selectTab(tab: Tabs) {
        selectedTab = tab
    }
}