package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("type") val type: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("uuid") val uuid: String
)
