package com.example.near.core.network.model.commmunity

import com.example.near.core.network.model.TemplateResponse
import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    @SerializedName("id") val id: String,
    @SerializedName("communityName") val communityName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String?,
    @SerializedName("district") val district: String?,
    @SerializedName("registrationDate") val registrationDate: String,
    @SerializedName("subscribers") val subscribers: List<SubscriberResponse>,
    @SerializedName("notificationTemplates") val notificationTemplates: List<TemplateResponse>
)
