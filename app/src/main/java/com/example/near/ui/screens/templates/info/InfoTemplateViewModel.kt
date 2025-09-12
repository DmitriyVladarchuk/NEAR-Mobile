package com.example.near.ui.screens.templates.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.usecase.SendTemplateUseCase
import com.example.near.feature.user.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InfoTemplateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAllFriendsInfoUseCase: GetAllFriendsInfoUseCase,
    private val sendTemplateUseCase: SendTemplateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(InfoTemplateState())
    val state: StateFlow<InfoTemplateState> get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<InfoTemplateEffect>()
    val effect: SharedFlow<InfoTemplateEffect> = _effect.asSharedFlow()

    fun onEvent(event: InfoTemplateEvent) {
        when (event) {
            is InfoTemplateEvent.LoadData -> loadData(event.templateId)
            is InfoTemplateEvent.ToggleRecipient -> toggleRecipient(event.friendId)
            is InfoTemplateEvent.ToggleGroupRecipients -> toggleGroupRecipients(event.members)
            is InfoTemplateEvent.SendTemplate -> sendTemplate()
        }
    }

    private fun loadData(templateId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val user = getUserUseCase()
                val template = user?.notificationTemplates?.find { it.id == templateId }
                val friends = getAllFriendsInfoUseCase().getOrThrow()
                val groups = user?.groups ?: emptyList()

                _state.update {
                    it.copy(
                        template = template,
                        friends = friends,
                        groups = groups,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val error = when (e) {
                    is IOException -> TemplateError.NetworkError(e.message)
                    else -> TemplateError.UnknownError(e.message)
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
                }
                _effect.emit(InfoTemplateEffect.ShowError(error))
            }
        }
    }

    private fun toggleRecipient(friendId: String) {
        val currentRecipients = _state.value.recipients
        val newRecipients = if (currentRecipients.contains(friendId)) {
            currentRecipients.filter { it != friendId }
        } else {
            currentRecipients + friendId
        }

        _state.update { it.copy(recipients = newRecipients) }
    }

    private fun toggleGroupRecipients(members: List<String>) {
        val currentRecipients = _state.value.recipients
        val allMembersSelected = members.all { it in currentRecipients }

        val newRecipients = if (allMembersSelected) {
            currentRecipients.filter { it !in members }
        } else {
            currentRecipients + members.filter { it !in currentRecipients }
        }

        _state.update { it.copy(recipients = newRecipients) }
    }

    private fun sendTemplate() {
        viewModelScope.launch {
            val template = _state.value.template
            val recipients = _state.value.recipients

            try {
                val result = sendTemplateUseCase(
                    SendTemplateParams(
                        templateId = template!!.id,
                        recipients = recipients
                    )
                )

                if (result.isSuccess) {
                    _effect.emit(InfoTemplateEffect.NavigateBack)
                } else {
                    _effect.emit(InfoTemplateEffect.ShowError(TemplateError.SendTemplateFailed))
                }
            } catch (e: Exception) {
                val error = when (e) {
                    is IOException -> TemplateError.NetworkError(e.message)
                    else -> TemplateError.UnknownError(e.message)
                }
                _effect.emit(InfoTemplateEffect.ShowError(error))
            }
        }
    }
}