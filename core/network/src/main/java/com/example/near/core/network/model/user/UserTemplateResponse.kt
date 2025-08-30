package com.example.near.core.network.model.user

import com.example.near.core.network.model.EmergencyTypeResponse
import com.google.gson.annotations.SerializedName

data class UserTemplateResponse(
    @SerializedName("id") val id: String,
    @SerializedName("templateName") val templateName: String,
    @SerializedName("message") val message: String,
    @SerializedName("emergencyType") val emergencyType: EmergencyTypeResponse
)
