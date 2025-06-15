package com.example.near.data.shared.models

import com.google.gson.annotations.SerializedName

data class SignupNotificationOptionRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("notificationOption") val notificationOption: String
)
