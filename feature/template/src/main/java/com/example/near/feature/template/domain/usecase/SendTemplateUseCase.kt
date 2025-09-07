package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.repository.TemplateRepository

class SendTemplateUseCase(
    private val userRepository: TemplateRepository,
) {
    suspend operator fun invoke(params: SendTemplateParams): Result<Unit> {
        return userRepository.sendTemplate(params = params)
    }
}