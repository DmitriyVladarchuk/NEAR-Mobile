package com.example.near.data.shared.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("type") val type: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("uuid") val uuid: String
)
