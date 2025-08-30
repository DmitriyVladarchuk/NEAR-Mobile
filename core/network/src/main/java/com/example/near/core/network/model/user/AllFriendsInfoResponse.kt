package com.example.near.core.network.model.user

import com.example.near.domain.user.models.User

data class AllFriendsInfoResponse(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
