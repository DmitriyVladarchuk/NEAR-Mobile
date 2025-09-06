package com.example.near.feature.user.domain.models

data class AllFriendsInfo(
    val friends: List<User>,
    val sentRequests: List<User>,
    val receivedRequests: List<User>
)
