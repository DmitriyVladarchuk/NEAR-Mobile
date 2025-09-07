package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.repository.TemplateRepository

class CreateTemplateUseCase(
    private val userRepository: TemplateRepository,
) {
    suspend operator fun invoke(template: CreateTemplate): Result<Unit> {
        return userRepository.createTemplate(template)
    }
}