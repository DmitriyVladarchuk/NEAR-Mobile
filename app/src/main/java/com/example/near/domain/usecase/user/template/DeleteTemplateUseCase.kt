package com.example.near.domain.usecase.user.template

import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class DeleteTemplateUseCase @Inject constructor(
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