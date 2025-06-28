package com.example.near.ui.screens.subscriptions

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CommunityTab { SUBSCRIPTION, ALL, SEARCH }

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAllCommunitiesUseCase: GetAllCommunitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

    private val _selectedTab = MutableStateFlow(CommunityTab.SUBSCRIPTION)
    val selectedTab: StateFlow<CommunityTab> = _selectedTab

    private val _communities = MutableStateFlow<List<UserSubscription>>(emptyList())
    val communities: StateFlow<List<UserSubscription>> = _communities

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        when (selectedTab.value) {
            CommunityTab.SUBSCRIPTION -> loadSubscriptions()
            CommunityTab.ALL -> loadAllCommunities()
            CommunityTab.SEARCH -> Unit
        }
    }

    fun selectTab(tab: CommunityTab) {
        _selectedTab.value = tab
        loadInitialData()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        // TODO поиск
    }

    private fun loadSubscriptions() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val result = getUserUseCase()?.subscriptions
                // Временная заглушка
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
            val result = getAllCommunitiesUseCase()
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