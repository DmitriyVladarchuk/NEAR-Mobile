package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class TemplateActionRequest(
    @SerializedName("templateId") val templateId: String,
    @SerializedName("templateName") val templateName: String,
    @SerializedName("message") val message: String,
    @SerializedName("emergencyType") val emergencyType: EmergencyType,
)
