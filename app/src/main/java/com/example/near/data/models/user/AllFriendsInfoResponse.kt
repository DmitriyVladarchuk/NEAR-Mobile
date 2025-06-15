package com.example.near.data.models.user

import com.example.near.domain.models.user.User

data class AllFriendsInfoResponse(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
