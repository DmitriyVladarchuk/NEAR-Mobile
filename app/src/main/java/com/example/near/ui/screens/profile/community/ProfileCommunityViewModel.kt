package com.example.near.ui.screens.profile.community

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.data.models.community.CommunityResponse
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.community.GetCommunityUseCase
import com.example.near.domain.usecase.user.UserCancelSubscribeUseCase
import com.example.near.domain.usecase.user.UserSubscribeUseCase
import com.example.near.domain.usecase.user.auth.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCommunityViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val subscribeUseCase: UserSubscribeUseCase,
    private val cancelSubscribeUseCase: UserCancelSubscribeUseCase
) : ViewModel() {
    val avatarUrl: String = ""
    var community by mutableStateOf<CommunityResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var isSubscribe by mutableStateOf<Boolean>(false)
        private set


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
                val user = getUserUseCase()
                val sub = user!!.subscriptions.find { it.id == communityId  }
                community = CommunityResponse(
                    id = sub!!.id,
                    communityName = sub!!.communityName,
                    description = sub?.description,
                    country = sub.country,
                    city = sub.city,
                    district = null,
                    registrationDate = "",
                    subscribers = listOf(),
                    notificationTemplates = listOf()
                )
                Log.d("Test", community.toString())
            } catch (e: Exception) {
                error = e.message ?: "Failed to load community data"
            } finally {
                isLoading = false
            }
        }
    }

    fun handleSubscribe(communityId: String) {
        viewModelScope.launch {
            subscribeUseCase(communityId)
        }
    }

    fun logOut(logOutEvent: () -> Unit) {
        viewModelScope.launch {
            logOutUseCase()
            logOutEvent()
        }
    }
}