package com.example.near.core.network.model.user

data class AllFriendsInfoResponse(
    val friends: List<UserResponse>,
    val sentRequests: List<UserResponse>,
    val receivedRequests: List<UserResponse>
)
