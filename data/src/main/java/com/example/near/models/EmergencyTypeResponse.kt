package com.example.near.data.shared.models

import com.google.gson.annotations.SerializedName

data class EmergencyTypeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("bgColor") val bgColor: String
)
