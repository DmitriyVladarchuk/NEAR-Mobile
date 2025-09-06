package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken") val refreshToken: String
)
