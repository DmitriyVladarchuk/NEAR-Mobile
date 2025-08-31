package com.example.near.feature.user.domain.usecase

import com.example.near.feature.user.domain.repository.UserRepository

class UpdateUserUseCase(
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