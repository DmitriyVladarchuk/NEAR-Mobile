package com.example.near.ui.screens.friendsAndGroups.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.models.UserFriend
import com.example.near.domain.user.models.UserGroup
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.user.usecase.group.CreateGroupUseCase
import com.example.near.domain.user.usecase.group.DeleteGroupUseCase
import com.example.near.domain.user.usecase.group.UpdateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val getUser: GetUserUseCase,
): ViewModel() {
    var friends: List<UserFriend> by mutableStateOf(listOf())

    var groups: List<UserGroup> by mutableStateOf(listOf())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadFriends()
    }

    fun loadFriends() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val user = getUser()
                friends = user?.friends ?: listOf()
                groups = user?.groups ?: listOf()
            } catch (e: Exception) {
                error = e.message ?: "Failed to load user data"
            } finally {
                isLoading = false
            }
        }
    }

    fun createGroup(groupName: String, members: List<String>) {
        viewModelScope.launch {
            createGroupUseCase(groupName, members)
            loadFriends()
        }
    }

    fun updateGroup(groupId: String, groupName: String, members: List<String>) {
        viewModelScope.launch {
            updateGroupUseCase(groupId, groupName, members)
            loadFriends()
        }
    }
}