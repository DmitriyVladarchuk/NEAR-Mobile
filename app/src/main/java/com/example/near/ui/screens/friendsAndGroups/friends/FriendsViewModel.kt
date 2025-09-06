package com.example.near.ui.screens.friendsAndGroups.friends

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import com.example.near.feature.user.domain.models.AllFriendsInfo
import com.example.near.feature.user.domain.models.User
import com.example.near.feature.user.domain.usecase.GetUserByIdUseCase
import com.example.near.feature.user.domain.usecase.GetUserUseCase
import com.example.near.feature.user.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FriendsTab { ALL, REQUESTS, SEARCH }

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getAllFriendsInfo: GetAllFriendsInfoUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val searchUsersUseCase: SearchUsersUseCase
): ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    private val _selectedTab = mutableStateOf(FriendsTab.ALL)
    val selectedTab: State<FriendsTab> = _selectedTab

    private val _friendsData = mutableStateOf<AllFriendsInfo?>(null)
    val friendsData: State<AllFriendsInfo?> = _friendsData

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchResults = mutableStateOf<List<User>>(emptyList())
    val searchResults: State<List<User>> = _searchResults

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        when (selectedTab.value) {
            FriendsTab.ALL -> loadAllFriends()
            FriendsTab.REQUESTS -> loadRequests()
            FriendsTab.SEARCH -> if (_searchQuery.value.isNotEmpty()) {
                searchFriends(_searchQuery.value)
            }
        }
    }

    fun selectTab(tab: FriendsTab) {
        _selectedTab.value = tab
        if (tab == FriendsTab.SEARCH && _searchQuery.value.isNotEmpty()) {
            searchFriends(_searchQuery.value)
        } else {
            loadInitialData()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotEmpty()) {
            searchFriends(query)
        } else {
            loadInitialData()
        }
    }

    private fun searchFriends(query: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            searchUsersUseCase(query)
                .onSuccess { usersList ->
                    _searchResults.value = usersList.content
                    _uiState.value = UIState.Success
                }
                .onFailure { e ->
                    _uiState.value = UIState.Error(e.message ?: "Failed to search users")
                }
        }
    }

    private fun loadAllFriends() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            getAllFriendsInfo()
                .onSuccess {
                    _friendsData.value = it
                    _uiState.value = UIState.Success
                }
                .onFailure { e ->
                    _uiState.value = UIState.Error(e.message ?: "Failed to load friends")
                }
        }
    }

    private fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            getAllFriendsInfo()
                .onSuccess {
                    _friendsData.value = it
                    _uiState.value = UIState.Success
                }
                .onFailure { e ->
                    _uiState.value = UIState.Error(e.message ?: "Failed to load requests")
                }
        }
    }
}