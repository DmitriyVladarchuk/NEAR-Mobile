package com.example.near.ui.screens.auth.signup.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.models.common.emergencyTypes
import com.example.near.domain.usecase.community.SignUpCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupCommunityViewModel @Inject constructor(
    private val signUpCommunityUseCase: SignUpCommunityUseCase
): ViewModel() {
    var communityName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var monitoringRegion by mutableStateOf("")
    var emergencyType by mutableStateOf("")

    fun onSignUpClick(navigateToDashboards: () -> Unit) {
        viewModelScope.launch {
            if (signUpCommunityUseCase(communityName, email, password, monitoringRegion, listOf(
                    emergencyTypes.get(0))).isSuccess) {
                navigateToDashboards()
            }
        }
    }
}