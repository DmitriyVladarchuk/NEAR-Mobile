package com.example.near.domain.models.user

data class AllFriendsInfo(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
