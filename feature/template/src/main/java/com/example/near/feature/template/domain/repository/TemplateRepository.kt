package com.example.near.feature.template.domain.repository

import com.example.near.feature.template.domain.model.*

interface TemplateRepository {
    suspend fun getTemplates(): Result<List<Template>>
    suspend fun createTemplate(template: CreateTemplate): Result<Unit>
    suspend fun updateTemplate(template: Template): Result<Unit>
    suspend fun deleteTemplate(template: Template): Result<Unit>
    suspend fun sendTemplate(params: SendTemplateParams): Result<Unit>
}