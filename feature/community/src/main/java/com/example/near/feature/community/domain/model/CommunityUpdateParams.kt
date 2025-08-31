package com.example.near.feature.community.domain.model

data class CommunityUpdateParams(
    val communityName: String? = null,
    val description: String? = null,
    val country: String? = null,
    val city: String? = null,
    val district: String? = null,
    val emergencyTypeIds: List<Int>? = null
)
