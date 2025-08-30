package com.example.near.domain.community.repository

import com.example.near.common.models.EmergencyType
import com.example.near.community.models.CommunityUpdateParams
import com.example.near.domain.community.models.Community

interface CommunityRepository {

    suspend fun getCommunityInfo(): Result<Community>

    suspend fun updateCommunity(communityUpdateParams: CommunityUpdateParams): Result<Unit>

//    suspend fun getEmergencyType(): Result<List<EmergencyType>>

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