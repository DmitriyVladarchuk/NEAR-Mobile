package com.example.near.ui.screens.community.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.common.usecase.GetCommunityByIdUseCase
import com.example.near.domain.community.models.Community
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.domain.user.usecase.UserCancelSubscribeUseCase
import com.example.near.domain.user.usecase.UserSubscribeUseCase
import com.example.near.domain.user.usecase.auth.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SubscriptionStatus {
    SUBSCRIBE,
    NOT_SUBSCRIBE
}

@HiltViewModel
class ProfileCommunityViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase,
    private val getCommunityByIdUseCase: GetCommunityByIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val subscribeUseCase: UserSubscribeUseCase,
    private val cancelSubscribeUseCase: UserCancelSubscribeUseCase
) : ViewModel() {

    val avatarUrl: String = ""
    var community by mutableStateOf<Community?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var subscriptionStatus by mutableStateOf<SubscriptionStatus>(SubscriptionStatus.NOT_SUBSCRIBE)
        private set


    fun loadCommunity() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                community = getCommunityUseCase()
                Log.d("CommunityInfo", community.toString())
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
                community = getCommunityByIdUseCase(communityId)
                checkSubscriptionStatus(communityId)
            } catch (e: Exception) {
                error = e.message ?: "Failed to load community data"
            } finally {
                isLoading = false
            }
        }
    }

    fun handleSubscribe(communityId: String) {
        viewModelScope.launch {
            when(subscriptionStatus) {
                SubscriptionStatus.SUBSCRIBE -> cancelSubscribeUseCase(communityId)
                SubscriptionStatus.NOT_SUBSCRIBE -> subscribeUseCase(communityId)
            }
        }
    }

    fun logOut(logOutEvent: () -> Unit) {
        viewModelScope.launch {
            logOutUseCase()
            logOutEvent()
        }
    }

    private suspend fun checkSubscriptionStatus(communityId: String) {
        val currentUser = getUserUseCase()
        subscriptionStatus =  when {
            currentUser!!.subscriptions.any { it.id == communityId } -> SubscriptionStatus.SUBSCRIBE
            else -> SubscriptionStatus.NOT_SUBSCRIBE
        }
    }
}