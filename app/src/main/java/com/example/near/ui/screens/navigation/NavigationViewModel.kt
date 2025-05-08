package com.example.near.ui.screens.navigation

import androidx.lifecycle.ViewModel
import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.datastore.SettingsDataStorage
import com.example.near.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    val sessionManager: SessionManager,
    val authDataStorage: AuthDataStorage,
    val settingsDataStorage: SettingsDataStorage,
    val userRepository: UserRepository
) : ViewModel() {
}