package com.example.near.data.models

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("type")
    val type: String,

    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String,

    @SerializedName("uuid")
    val uuid: String
)
