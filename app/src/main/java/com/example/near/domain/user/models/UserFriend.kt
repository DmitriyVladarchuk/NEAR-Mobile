package com.example.near.domain.user.models

data class UserFriend(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val age: Int,
    val country: String,
    val city: String,
    val district: String,
    val registrationDate: String,
    val friendsCount: Int,
    val subscriptionCount: Int,
)
