package com.example.near.domain.repository

import com.example.near.data.models.LoginResponse
import com.example.near.data.models.community.CommunityResponse
import com.example.near.domain.models.EmergencyType

interface CommunityRepository {
    suspend fun signUp(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Result<LoginResponse>

    suspend fun getCommunityInfo(): Result<CommunityResponse>
}