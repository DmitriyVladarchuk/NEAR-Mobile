package com.example.near.domain.user.usecase

import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.repository.UserRepository

class GetNotificationOptionsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<NotificationOption>> {
        return userRepository.getNotificationOptions()
    }
}