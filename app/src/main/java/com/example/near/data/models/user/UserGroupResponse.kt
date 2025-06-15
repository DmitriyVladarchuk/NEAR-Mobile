package com.example.near.data.models.user

import com.google.gson.annotations.SerializedName

data class UserGroupResponse(
    @SerializedName("id") val id: String,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("members") val members: List<UserFriendResponse>
)
