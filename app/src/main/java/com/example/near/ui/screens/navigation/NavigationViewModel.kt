package com.example.near.ui.screens.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.data.storage.AuthDataStorage
import com.example.near.data.storage.SessionManager
import com.example.near.data.storage.SettingsDataStorage
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.user.auth.LoadUserUseCase
import com.example.near.service.FcmTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    val sessionManager: SessionManager,
    val authDataStorage: AuthDataStorage,
    val settingsDataStorage: SettingsDataStorage,
    val userRepository: UserRepository,
    val communityRepository: CommunityRepository,
    val loadUserUseCase: LoadUserUseCase,
    val fcmTokenManager: FcmTokenManager
) : ViewModel() {

    fun refreshToken() {
        viewModelScope.launch {
            fcmTokenManager.forceGenerateNewToken()

        }
    }
}