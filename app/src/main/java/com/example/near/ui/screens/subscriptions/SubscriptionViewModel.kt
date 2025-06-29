package com.example.near.ui.screens.subscriptions

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.user.models.UserFriend
import com.example.near.domain.user.models.UserSubscription
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.user.usecase.GetAllCommunitiesUseCase
import com.example.near.user.usecase.SearchCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CommunityTab { SUBSCRIPTION, ALL, SEARCH }

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAllCommunitiesUseCase: GetAllCommunitiesUseCase,
    private val searchCommunityUseCase: SearchCommunityUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf<UIState>(UIState.Idle)
    val uiState: State<UIState> = _uiState

    private val _selectedTab = mutableStateOf(CommunityTab.SUBSCRIPTION)
    val selectedTab: State<CommunityTab> = _selectedTab

    private val _communities = mutableStateOf<List<UserSubscription>>(emptyList())
    val communities: State<List<UserSubscription>> = _communities

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        when (selectedTab.value) {
            CommunityTab.SUBSCRIPTION -> loadSubscriptions()
            CommunityTab.ALL -> loadAllCommunities()
            CommunityTab.SEARCH -> if (_searchQuery.value.isNotEmpty()) {
                searchCommunities(_searchQuery.value)
            }
        }
    }

    fun selectTab(tab: CommunityTab) {
        _selectedTab.value = tab
        if (tab == CommunityTab.SEARCH && _searchQuery.value.isNotEmpty()) {
            searchCommunities(_searchQuery.value)
        } else {
            loadInitialData()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotEmpty()) {
            searchCommunities(query)
        } else {
            loadInitialData()
        }
    }

    private fun searchCommunities(query: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            searchCommunityUseCase(query)
                .onSuccess { communitiesList ->
                    _communities.value = communitiesList.content
                    _uiState.value = UIState.Success
                }
                .onFailure { e ->
                    _uiState.value = UIState.Error(e.message ?: "Failed to search communities")
                }
        }
    }

    private fun loadSubscriptions() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val result = getUserUseCase()?.subscriptions
                _communities.value = result ?: emptyList()
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Failed to load subscriptions")
            }
        }
    }

    private fun loadAllCommunities() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            getAllCommunitiesUseCase()
                .onSuccess {
                    _communities.value = it.content
                    _uiState.value = UIState.Success
                }
                .onFailure {
                    _uiState.value = UIState.Error(it.message ?: "Failed to load communities")
                }
        }
    }
}