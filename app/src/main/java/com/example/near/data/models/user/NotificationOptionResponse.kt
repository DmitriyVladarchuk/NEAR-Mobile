package com.example.near.data.models.user

import com.google.gson.annotations.SerializedName

data class NotificationOptionResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("bgColor") val bgColor: String,
    @SerializedName("colorDark") val colorDark: String,
    @SerializedName("bgColorDark") val bgColorDark: String,
)
