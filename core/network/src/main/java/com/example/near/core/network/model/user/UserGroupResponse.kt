package com.example.near.core.network.model.user

import com.google.gson.annotations.SerializedName

data class UserGroupResponse(
    @SerializedName("id") val id: String,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("members") val members: List<UserFriendResponse>
)
