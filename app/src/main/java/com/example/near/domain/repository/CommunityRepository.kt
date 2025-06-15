package com.example.near.domain.repository

import com.example.near.domain.models.common.AuthTokens
import com.example.near.domain.models.common.EmergencyType
import com.example.near.domain.models.community.Community

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
    ): Result<AuthTokens>

    suspend fun getCommunityInfo(): Result<Community>

    suspend fun refreshToken(token: String): Result<AuthTokens>

    suspend fun sendFcmToken(token: String): Result<Unit>

    // --- Template actions ---

    suspend fun createTemplate(
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun updateTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun deleteTemplate(
        id: String,
        templateName: String,
        message: String,
        emergencyType: EmergencyType
    ): Result<Unit>

    suspend fun sendTemplate(
        templateId: String,
        recipients: List<String>
    ): Result<Unit>

}