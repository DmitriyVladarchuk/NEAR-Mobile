package com.example.near.feature.user.domain.usecase

import com.example.near.domain.shared.models.NotificationOption
import com.example.near.feature.user.domain.repository.UserRepository

class GetNotificationOptionsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<NotificationOption>> {
        return userRepository.getNotificationOptions()
    }
}