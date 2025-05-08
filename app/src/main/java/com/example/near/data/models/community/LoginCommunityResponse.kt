package com.example.near.data.models.community

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
