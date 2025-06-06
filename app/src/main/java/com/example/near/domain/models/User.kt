package com.example.near.domain.models

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val age: Int,
    val country: String,
    val city: String,
    val district: String,
    val registrationDate: String,
    val deviceToken: String,
    val friends: List<UserFriend>,
    val groups: List<UserGroup>,
    val subscriptions: List<UserSubscription>,
    val notificationTemplates: List<UserTemplate>,
)
