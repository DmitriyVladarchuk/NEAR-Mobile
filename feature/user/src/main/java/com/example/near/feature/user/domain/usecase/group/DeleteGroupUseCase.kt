package com.example.near.domain.user.usecase.group

import com.example.near.feature.user.domain.repository.UserRepository

class DeleteGroupUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String, groupName: String, members: List<String>): Result<Unit> {
        return userRepository.deleteGroup(id, groupName, members)
    }
}