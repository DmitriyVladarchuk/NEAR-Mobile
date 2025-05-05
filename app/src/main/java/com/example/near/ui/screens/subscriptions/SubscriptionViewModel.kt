package com.example.near.ui.screens.subscriptions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.UserFriend
import com.example.near.domain.models.UserSubscription
import com.example.near.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CommunityTab { SUBSCRIPTION, ALL, SEARCH }

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var subscriptions: List<UserSubscription> by mutableStateOf(listOf())
        private set

    var selectedTab by mutableStateOf(CommunityTab.SUBSCRIPTION)
    var searchQuery by mutableStateOf("")
    var searchResults by mutableStateOf<List<UserFriend>>(emptyList())


    init {
        loadFriends()
    }

    fun loadFriends() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                subscriptions = getUser()?.subscriptions ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun selectTab(tab: CommunityTab) {
        selectedTab = tab
    }

    fun search(query: String) {
//        searchQuery = query
//        searchResults = if (query.isEmpty()) {
//            emptyList()
//        } else {
//
//        }
    }
}