package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository

class GetTemplatesUseCase(
    private val templateRepository: TemplateRepository
) {
    suspend operator fun invoke(): Result<List<Template>> {
        return templateRepository.getTemplates()
    }
}