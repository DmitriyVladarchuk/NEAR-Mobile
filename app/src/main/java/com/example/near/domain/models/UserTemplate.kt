package com.example.near.domain.models

import com.google.gson.annotations.SerializedName

data class UserTemplate(
    @SerializedName("templateId") val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType,
    @SerializedName("id")val idCommunity: String? = null
)

data class CommunityTemplate(
    val id: String,
    val templateName: String,
    val message: String,
    val emergencyType: EmergencyType
)
