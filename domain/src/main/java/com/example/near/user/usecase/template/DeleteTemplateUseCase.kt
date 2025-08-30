package com.example.near.domain.shared.usecase.template

import com.example.near.common.models.EmergencyType
import com.example.near.domain.user.repository.UserRepository

class DeleteTemplateUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return userRepository.deleteTemplate(id, templateName, message, emergencyType)
    }
}