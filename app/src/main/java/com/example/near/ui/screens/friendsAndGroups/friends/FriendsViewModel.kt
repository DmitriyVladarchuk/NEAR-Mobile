package com.example.near.ui.screens.friendsAndGroups.friends

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.models.User
import com.example.near.domain.shared.usecase.GetUserByIdUseCase
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FriendsTab { ALL, REQUESTS, SEARCH }

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getAllFriendsInfo: GetAllFriendsInfoUseCase,
    private val getUserById: GetUserByIdUseCase
): ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var friendsData by mutableStateOf<AllFriendsInfo?>(null)
        private set

    var selectedTab by mutableStateOf(FriendsTab.ALL)
    var searchQuery by mutableStateOf("")
    var searchResults by mutableStateOf<List<User>>(emptyList())


    init {
        loadFriends()
    }

    fun loadFriends() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                friendsData = getAllFriendsInfo().getOrThrow()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load friends data"
            } finally {
                isLoading = false
            }
        }
    }

    fun selectTab(tab: FriendsTab) {
        selectedTab = tab
    }

    fun search(query: String) {
        searchQuery = query
        if (query.isEmpty()) {
            searchResults = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val user = getUserById(query)
                Log.d("Test", user.toString())
                user?.let {
                    searchResults = listOf(it)
                } ?: run {
                    searchResults = emptyList()
                }
            } catch (e: Exception) {
                searchResults = emptyList()
            }
        }
    }

    fun clearSearch() {
        searchQuery = ""
        searchResults = emptyList()
        selectedTab = FriendsTab.ALL
    }
}