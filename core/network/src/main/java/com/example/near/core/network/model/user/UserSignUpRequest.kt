package com.example.near.core.network.model.user

import com.example.near.core.network.model.SignupNotificationOptionRequest
import com.google.gson.annotations.SerializedName

data class UserSignUpRequest(
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("telegramShortName") val telegramShortName: String,
    @SerializedName("location") val location: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("selectedOptions") val selectedOptions: List<SignupNotificationOptionRequest>
)
