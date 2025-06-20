package com.example.near.data.user.models

import com.example.near.domain.user.models.User

data class AllFriendsInfoResponse(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
