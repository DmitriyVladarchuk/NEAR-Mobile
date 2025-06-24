package com.example.near.community.models

data class CommunityUpdateParams(
    val communityName: String? = null,
    val description: String? = null,
    val country: String? = null,
    val city: String? = null,
    val district: String? = null,
    val emergencyTypeIds: List<Int>? = null
)
