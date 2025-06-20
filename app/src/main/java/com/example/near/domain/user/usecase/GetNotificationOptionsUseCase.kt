package com.example.near.domain.user.usecase

import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class GetNotificationOptionsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<NotificationOption>> {
        return userRepository.getNotificationOptions()
    }
}