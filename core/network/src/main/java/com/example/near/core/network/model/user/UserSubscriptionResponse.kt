package com.example.near.core.network.model.user

import com.google.gson.annotations.SerializedName

data class UserSubscriptionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("communityName") val communityName: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String? = null,
    @SerializedName("district") val district: String? = null,
    @SerializedName("registrationDate") val registrationDate: String,
    @SerializedName("subscribersCount") val subscribersCount: Int
)
