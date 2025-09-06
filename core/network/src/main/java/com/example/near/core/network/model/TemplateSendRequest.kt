package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class TemplateSendRequest(
    @SerializedName("templateId") val templateId: String,
    @SerializedName("recipients") val recipients: List<String>
)
