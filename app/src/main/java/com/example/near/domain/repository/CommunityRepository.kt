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

    suspend fun refreshToken(token: String): Result<LoginResponse>

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