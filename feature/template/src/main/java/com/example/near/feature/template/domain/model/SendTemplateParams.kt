package com.example.near.feature.template.domain.model

data class SendTemplateParams(
    val templateId: String,
    val recipients: List<String>
)
