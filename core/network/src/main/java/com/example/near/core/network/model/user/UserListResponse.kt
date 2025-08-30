package com.example.near.core.network.model.user

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("content") val content: List<UserResponse>,
)
