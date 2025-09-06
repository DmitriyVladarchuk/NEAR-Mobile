package com.example.near.feature.community.domain.model

import com.example.near.feature.template.domain.model.Template

data class Community(
    val id: String,
    val communityName: String,
    val description: String?,
    val country: String,
    val city: String?,
    val district: String?,
    val registrationDate: String,
    val subscribers: List<Subscriber>,
    val notificationTemplates: List<Template>
)
