package com.example.near.data.shared.models

import com.google.gson.annotations.SerializedName

data class TemplateSendRequest(
    @SerializedName("templateId") val templateId: String,
    @SerializedName("recipients") val recipients: List<String>
)
