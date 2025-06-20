package com.example.near.domain.user.usecase

import com.example.near.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String? = null,
        lastName: String? = null,
        birthday: String? = null,
        country: String? = null,
        city: String? = null,
        district: String? = null,
        selectedOptions: List<Int>? = null
    ): Result<Unit> {
        return userRepository.updateUser(
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            country = country,
            city = city,
            district = district,
            selectedOptions = selectedOptions
        )
    }
}