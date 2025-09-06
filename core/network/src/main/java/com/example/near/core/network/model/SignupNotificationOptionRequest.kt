package com.example.near.core.network.model

import com.google.gson.annotations.SerializedName

data class SignupNotificationOptionRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("notificationOption") val notificationOption: String
)
