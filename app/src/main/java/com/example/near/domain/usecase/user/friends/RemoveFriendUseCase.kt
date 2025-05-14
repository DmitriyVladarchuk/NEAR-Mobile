package com.example.near.domain.usecase.user.friends

import android.util.Log
import com.example.near.domain.repository.UserRepository
import javax.inject.Inject

class RemoveFriendUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(friendId: String): Result<Unit> {
        Log.d("RemoveFriend", friendId)
        val result = userRepository.removeFriend(friendId)
        if (result.isFailure) {
            Log.e("RemoveFriend", "Failed to remove friend")
        }
        return result
    }
}