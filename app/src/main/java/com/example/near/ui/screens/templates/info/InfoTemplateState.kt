package com.example.near.ui.screens.templates.info

import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.user.domain.models.AllFriendsInfo
import com.example.near.feature.user.domain.models.UserGroup

data class InfoTemplateState(
    val template: Template? = null,
    val friends: AllFriendsInfo? = null,
    val groups: List<UserGroup> = emptyList(),
    val recipients: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: TemplateError? = null
)

sealed class TemplateError {
    data object LoadDataFailed : TemplateError()
    data object SendTemplateFailed : TemplateError()
    data class NetworkError(val message: String?) : TemplateError()
    data class UnknownError(val message: String?) : TemplateError()
}
