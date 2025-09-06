package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class EmergencyTypeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("bgColor") val bgColor: String
)
