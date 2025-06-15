package com.example.near.domain.usecase.user.template

import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class CreateTemplateUseCase @Inject constructor(
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