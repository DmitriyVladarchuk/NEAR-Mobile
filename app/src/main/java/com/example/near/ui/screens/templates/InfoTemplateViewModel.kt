package com.example.near.ui.screens.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.models.UserFriend
import com.example.near.domain.user.models.UserGroup
import com.example.near.user.models.UserTemplate
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoTemplateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAllFriendsInfoUseCase: GetAllFriendsInfoUseCase,
) : ViewModel() {

    var template by mutableStateOf<UserTemplate?>(null)
        private set

    var friends by mutableStateOf<AllFriendsInfo?>(null)
        private set

    var subscribers by mutableStateOf<List<UserFriend>?>(null)
        private set

    var groups by mutableStateOf<List<UserGroup>?>(null)
        private set

    var recipients by mutableStateOf<List<String>>(emptyList())
        private set

    fun loadData(templateId: String) {
        viewModelScope.launch {
            val user = getUserUseCase()
            template = user?.notificationTemplates?.find { it.id == templateId }
            friends = getAllFriendsInfoUseCase().getOrThrow()
            groups = user?.groups

        }
    }

    fun toggleRecipient(friendId: String) {
        recipients = if (recipients.contains(friendId)) {
            recipients.filter { it != friendId }
        } else {
            recipients + friendId
        }
    }

    fun toggleGroupRecipients(members: List<String>) {
        val allMembersSelected = members.all { it in recipients }

        recipients = if (allMembersSelected) {
            recipients.filter { it !in members }
        } else {
            recipients + members.filter { it !in recipients }
        }
    }

    fun saveRecipients() {
        viewModelScope.launch {
            template?.let {
                //communityRepository.sendTemplate(template!!.id, recipients)
            }
        }
    }

}