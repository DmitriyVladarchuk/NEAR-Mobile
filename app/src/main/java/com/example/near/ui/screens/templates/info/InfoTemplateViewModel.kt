package com.example.near.ui.screens.templates.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.user.domain.models.AllFriendsInfo
import com.example.near.feature.user.domain.models.UserGroup
import com.example.near.feature.user.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoTemplateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAllFriendsInfoUseCase: GetAllFriendsInfoUseCase,
) : ViewModel() {

    var template by mutableStateOf<Template?>(null)
        private set

    var friends by mutableStateOf<AllFriendsInfo?>(null)
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