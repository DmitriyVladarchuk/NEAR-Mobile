package com.example.near.core.network.model.commmunity

import com.example.near.domain.shared.models.EmergencyType
import com.google.gson.annotations.SerializedName

data class SignUpCommunityRequest(
    @SerializedName("communityName") val communityName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("location") val location: String,
    @SerializedName("monitoredEmergencyTypes") val monitoredEmergencyTypes: List<EmergencyType>
)
