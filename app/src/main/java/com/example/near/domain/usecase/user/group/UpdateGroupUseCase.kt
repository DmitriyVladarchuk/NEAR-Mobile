package com.example.near.domain.usecase.user.group

import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String, groupName: String, members: List<String>): Result<Unit> {
        return userRepository.updateGroup(id, groupName, members)
    }
}