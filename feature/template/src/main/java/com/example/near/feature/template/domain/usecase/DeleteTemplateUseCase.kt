package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.repository.TemplateRepository

class DeleteTemplateUseCase(
    private val userRepository: TemplateRepository,
) {
    suspend operator fun invoke(createTemplate: CreateTemplate): Result<Unit> {
        return userRepository.deleteTemplate(createTemplate)
    }
}