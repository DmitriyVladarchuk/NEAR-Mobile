package com.example.near.community.models

import com.google.gson.annotations.SerializedName

data class UpdateCommunityRequest(
    @SerializedName("communityName") val communityName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("district") val district: String?,
    @SerializedName("emergencyTypeIds") val emergencyTypeIds: List<Int>?
)
