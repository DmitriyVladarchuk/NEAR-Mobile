package com.example.near.domain.user.usecase.friends

import com.example.near.domain.user.models.AllFriendsInfo
import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class GetAllFriendsInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<AllFriendsInfo> {
        return userRepository.getAllFriendsInfo()
    }
}