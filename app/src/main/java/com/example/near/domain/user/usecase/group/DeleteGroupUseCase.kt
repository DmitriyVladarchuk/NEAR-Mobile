package com.example.near.domain.user.usecase.group

import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String, groupName: String, members: List<String>): Result<Unit> {
        return userRepository.deleteGroup(id, groupName, members)
    }
}