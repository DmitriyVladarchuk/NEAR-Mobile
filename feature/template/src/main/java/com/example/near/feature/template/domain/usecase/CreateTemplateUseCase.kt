package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository

class CreateTemplateUseCase(
    private val userRepository: TemplateRepository,
) {
    suspend operator fun invoke(template: Template): Result<Unit> {
        return userRepository.createTemplate(template)
    }
}