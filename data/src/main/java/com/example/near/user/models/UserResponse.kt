package com.example.near.data.user.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("age") val age: Int,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("district") val district: String,
    @SerializedName("registrationDate") val registrationDate: String,
    @SerializedName("deviceToken") val deviceToken: String? = null,
    @SerializedName("friends") val friends: List<UserFriendResponse>,
    @SerializedName("groups") val groups: List<UserGroupResponse>,
    @SerializedName("subscriptions") val subscriptions: List<UserSubscriptionResponse>,
    @SerializedName("notificationTemplates") val notificationTemplates: List<UserTemplateResponse>
)
