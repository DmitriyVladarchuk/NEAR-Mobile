package com.example.near.domain.models

data class AllFriendsInfoResponse(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
