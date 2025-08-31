package com.example.near.core.network.model.commmunity

import com.google.gson.annotations.SerializedName

data class SubscriberResponse(
    @SerializedName("id") val id: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("age") val age: Int,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("district") val district: String,
    @SerializedName("registrationDate") val registrationDate: String,
    @SerializedName("friendsCount") val friendsCount: Int,
    @SerializedName("subscriptionCount") val subscriptionCount: Int
)
