package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository

class UpdateTemplateUseCase(
    private val userRepository: TemplateRepository,
) {
    suspend operator fun invoke(createTemplate: Template): Result<Unit> {
        return userRepository.updateTemplate(createTemplate)
    }
}