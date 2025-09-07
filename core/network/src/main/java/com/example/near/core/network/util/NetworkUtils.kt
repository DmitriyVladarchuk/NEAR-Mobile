package com.example.near.core.network.util

import retrofit2.Response
import java.io.IOException

object NetworkUtils {
    suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw IllegalStateException("Empty response body"))
            } else {
                Result.failure(Exception("HTTP error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }

    suspend fun executeVoidApiCall(apiCall: suspend () -> Response<Void>): Result<Unit> {
        return executeApiCall(apiCall).map {  }
    }
}