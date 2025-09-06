package com.example.near.domain.user.usecase.group

import com.example.near.feature.user.domain.repository.UserRepository

class UpdateGroupUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String, groupName: String, members: List<String>): Result<Unit> {
        return userRepository.updateGroup(id, groupName, members)
    }
}