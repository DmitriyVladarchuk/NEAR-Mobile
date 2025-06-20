package com.example.near.domain.user.models

data class AllFriendsInfo(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
