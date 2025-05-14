package com.example.near.domain.usecase.user.friends

import com.example.near.domain.models.AllFriendsInfoResponse
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class GetAllFriendsInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<AllFriendsInfoResponse> {
        return userRepository.getAllFriendsInfo()
    }
}