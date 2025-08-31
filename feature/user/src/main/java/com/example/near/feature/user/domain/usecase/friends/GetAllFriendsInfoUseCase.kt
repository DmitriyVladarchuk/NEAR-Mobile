package com.example.near.domain.user.usecase.friends

import com.example.near.feature.user.domain.models.AllFriendsInfo
import com.example.near.feature.user.domain.repository.UserRepository

class GetAllFriendsInfoUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<AllFriendsInfo> {
        return userRepository.getAllFriendsInfo()
    }
}