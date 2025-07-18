package com.example.near.domain.community.repository

import com.example.near.common.models.EmailVerificationStatus
import com.example.near.community.models.CommunityUpdateParams
import com.example.near.domain.shared.models.AuthTokens
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.domain.community.models.Community
import com.example.near.domain.shared.models.LoginCredentials

interface CommunityRepository {
    suspend fun signUp(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<EmailVerificationStatus>

    suspend fun login(credentials: LoginCredentials): Result<EmailVerificationStatus>

    suspend fun getCommunityInfo(): Result<Community>

    suspend fun updateCommunity(communityUpdateParams: CommunityUpdateParams): Result<Unit>

    suspend fun refreshToken(): Result<Unit>

    suspend fun getEmergencyType(): Result<List<EmergencyType>>

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