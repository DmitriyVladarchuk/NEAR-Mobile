package com.example.near.domain.usecase.user.template

import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UpdateTemplateUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit> {
        return userRepository.updateTemplate(id, templateName, message, emergencyType)
    }
}