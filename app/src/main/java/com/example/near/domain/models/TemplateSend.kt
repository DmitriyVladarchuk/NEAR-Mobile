package com.example.near.domain.models

data class TemplateSend(
    val templateId: String,
    val recipients: List<String>
)
