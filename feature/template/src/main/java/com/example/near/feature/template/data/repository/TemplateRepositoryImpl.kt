package com.example.near.feature.template.data.repository

import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository

class TemplateRepositoryImpl() : TemplateRepository {
    override suspend fun createTemplate(template: Template): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTemplate(createTemplate: CreateTemplate): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTemplate(createTemplate: CreateTemplate): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun sendTemplate(
        templateId: String,
        recipients: List<String>
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}