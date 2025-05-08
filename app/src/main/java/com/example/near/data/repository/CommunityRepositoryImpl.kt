package com.example.near.data.repository

import com.example.near.data.API.CommunityService
import com.example.near.data.datastore.SessionManager
import com.example.near.data.models.LoginRequest
import com.example.near.data.models.LoginResponse
import com.example.near.data.models.community.CommunityResponse
import com.example.near.data.models.community.SignUpCommunityRequest
import com.example.near.domain.models.EmergencyType
import com.example.near.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityService,
    private val sessionManager: SessionManager
) : CommunityRepository {

    override suspend fun signUp(
        communityName: String,
        email: String,
        password: String,
        location: String,
        monitoredEmergencyTypes: List<EmergencyType>
    ): Result<Unit> {
        return try {
            val response = communityService.signUp(
                SignUpCommunityRequest(
                    communityName,
                    email,
                    password,
                    location,
                    monitoredEmergencyTypes
                )
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Result.failure(Exception("Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = communityService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommunityInfo(): Result<CommunityResponse> {
        return try {
            val response = communityService.getCommunityInfo("Bearer ${sessionManager.authToken!!.accessToken}")
            if (response.isSuccessful) {
                response.body()?.let {
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
}