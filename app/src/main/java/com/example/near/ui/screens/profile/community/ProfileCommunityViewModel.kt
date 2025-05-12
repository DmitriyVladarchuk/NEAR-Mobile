package com.example.near.ui.screens.profile.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.data.models.community.CommunityResponse
import com.example.near.domain.usecase.community.GetCommunityUseCase
import com.example.near.domain.usecase.user.auth.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCommunityViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {
    val avatarUrl: String = ""
    var community by mutableStateOf<CommunityResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadCommunity()
    }

    fun loadCommunity() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                community = getCommunityUseCase()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load community data"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadCommunity(communityId: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                //community = getCommunityUseCase()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load community data"
            } finally {
                isLoading = false
            }
        }
    }

    fun logOut(logOutEvent: () -> Unit) {
        viewModelScope.launch {
            logOutUseCase()
            logOutEvent()
        }
    }
}