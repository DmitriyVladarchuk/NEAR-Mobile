package com.example.near.ui.screens.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.UserFriend
import com.example.near.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FriendsTab { ALL, REQUESTS, SEARCH }

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
): ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var friends: List<UserFriend> by mutableStateOf(listOf())
        private set

    var friendsRequest: List<UserFriend> by mutableStateOf(listOf())
        private set

    var selectedTab by mutableStateOf(FriendsTab.ALL)
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
                // Здесь будет загрузка запросов в друзья
                // friendsRequest = getUser()?.friendRequests ?: listOf()
                friends = getUser()?.friends ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun selectTab(tab: FriendsTab) {
        selectedTab = tab
//        if (tab == FriendsTab.REQUESTS) {
//            loadFriends()
//        }
    }

    fun search(query: String) {
        searchQuery = query
        searchResults = if (query.isEmpty()) {
            emptyList()
        } else {
            friends.filter { friend ->
                friend.firstName.contains(query, ignoreCase = true)
            }
        }
    }
}