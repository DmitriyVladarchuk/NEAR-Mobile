package com.example.near.feature.template.domain.repository

import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.Template

interface TemplateRepository {
    suspend fun createTemplate(template: Template): Result<Unit>
    suspend fun updateTemplate(createTemplate: CreateTemplate): Result<Unit>
    suspend fun deleteTemplate(createTemplate: CreateTemplate): Result<Unit>
    suspend fun sendTemplate(
        templateId: String,
        recipients: List<String>
    ): Result<Unit>
}