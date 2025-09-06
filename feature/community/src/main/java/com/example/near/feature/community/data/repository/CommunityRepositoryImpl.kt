package com.example.near.feature.community.data.repository

import com.example.near.core.network.SessionManager
import com.example.near.core.network.model.FcmTokenRequest
import com.example.near.core.network.service.CommunityService
import com.example.near.feature.community.data.mapper.toDomain
import com.example.near.feature.community.data.mapper.toRequest
import com.example.near.feature.community.domain.model.Community
import com.example.near.feature.community.domain.model.CommunityUpdateParams
import com.example.near.feature.community.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager,
) : CommunityRepository {

    override suspend fun getCommunityInfo(): Result<Community> {
        return try {
            val response =
                communityService.getCommunityInfo("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                response.body()?.toDomain()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCommunity(communityUpdateParams: CommunityUpdateParams): Result<Unit> {
        return try {
            val response = communityService.updateCommunity(
                token = "Bearer ${sessionManager.authToken!!.accessToken}",
                request = communityUpdateParams.toRequest()
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to update community profile request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    override suspend fun getEmergencyType(): Result<List<EmergencyType>> {
//        return try {
//            val response = communityService.getEmergencyType("Bearer ${sessionManager.authToken!!.accessToken}")
//            if (response.isSuccessful) {
//                val emergencyTypes = response.body()?.map { it.toDomain() }
//                Result.success(emergencyTypes ?: emptyList())
//            } else {
//                Result.failure(Exception("Failed getEmergencyType request"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    override suspend fun sendFcmToken(token: String): Result<Unit> {
        return try {
            val response = communityService.sendFcmToken(
                "Bearer ${sessionManager.authToken!!.accessToken}",
                FcmTokenRequest(token)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send token request"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}