package com.example.near.domain.shared.usecase.template

import com.example.near.common.models.EmergencyType
import com.example.near.domain.user.repository.UserRepository

class CreateTemplateUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return userRepository.createTemplate(templateName, message, emergencyType)
    }
}