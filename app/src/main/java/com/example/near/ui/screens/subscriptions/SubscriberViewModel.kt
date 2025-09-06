package com.example.near.ui.screens.subscriptions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.feature.community.domain.model.Subscriber
import com.example.near.feature.user.domain.models.UserFriend
import com.example.near.feature.community.domain.usecase.GetCommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriberViewModel @Inject constructor(
    private val getCommunityUseCase: GetCommunityUseCase
): ViewModel() {

    var subscribers by mutableStateOf<List<Subscriber>?>(null)
        private set

    init {
        viewModelScope.launch {
            subscribers = getCommunityUseCase().subscribers
        }
    }

}