package com.example.near.domain.usecase.user

import com.example.near.domain.models.common.NotificationOption
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class GetNotificationOptionsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<NotificationOption>> {
        return userRepository.getNotificationOptions()
    }
}