package com.example.near.domain.user.usecase.group

import com.example.near.domain.user.repository.UserRepository

class CreateGroupUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(groupName: String, members: List<String>): Result<Unit> {
        return userRepository.createGroup(groupName, members)
    }
}