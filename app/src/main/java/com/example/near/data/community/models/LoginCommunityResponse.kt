package com.example.near.data.community.models

import com.google.gson.annotations.SerializedName

data class LoginCommunityResponse(
    @SerializedName("type")
    val type: String,

    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String,

    @SerializedName("uuid")
    val uuid: String
)
