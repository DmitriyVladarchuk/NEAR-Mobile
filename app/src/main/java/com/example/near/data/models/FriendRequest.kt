package com.example.near.data.models

import com.google.gson.annotations.SerializedName

data class FriendRequest(
    @SerializedName("friendId")
    val friendId: String
)
