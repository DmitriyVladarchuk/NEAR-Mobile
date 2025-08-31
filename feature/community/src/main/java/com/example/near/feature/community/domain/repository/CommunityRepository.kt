package com.example.near.feature.community.domain.repository

import com.example.near.feature.community.domain.model.Community
import com.example.near.feature.community.domain.model.CommunityUpdateParams

interface CommunityRepository {

    suspend fun getCommunityInfo(): Result<Community>

    suspend fun updateCommunity(communityUpdateParams: CommunityUpdateParams): Result<Unit>

//    suspend fun getEmergencyType(): Result<List<EmergencyType>>

    suspend fun sendFcmToken(token: String): Result<Unit>

}