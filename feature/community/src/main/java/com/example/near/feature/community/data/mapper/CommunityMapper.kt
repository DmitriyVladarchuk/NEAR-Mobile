package com.example.near.feature.community.data.mapper

import com.example.near.core.network.model.TemplateResponse
import com.example.near.core.network.model.commmunity.CommunityResponse
import com.example.near.core.network.model.commmunity.SubscriberResponse
import com.example.near.core.network.model.commmunity.UpdateCommunityRequest
import com.example.near.feature.community.domain.model.Community
import com.example.near.feature.community.domain.model.CommunityUpdateParams
import com.example.near.feature.community.domain.model.Subscriber
import com.example.near.feature.template.domain.model.Template

fun CommunityResponse.toDomain(): Community = Community(
    id = id,
    communityName = communityName,
    description = description,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    subscribers = subscribers.toSubscriberList(),
    notificationTemplates = notificationTemplates.toTemplateList(),
)

fun CommunityUpdateParams.toRequest(): UpdateCommunityRequest {
    return UpdateCommunityRequest(
        communityName = communityName,
        description = description,
        country = country,
        city = city,
        district = district,
        emergencyTypeIds = emergencyTypeIds
    )
}

fun TemplateResponse.toDomain(): Template = Template(
    id = id,
    templateName = templateName,
    message = message,
    emergencyType = emergencyType
)

fun List<TemplateResponse>.toTemplateList(): List<Template> =
    this.map { it.toDomain() }

fun SubscriberResponse.toDomain(): Subscriber = Subscriber(
    id = id,
    firstName = firstName,
    lastName = lastName,
    birthday = birthday,
    age = age,
    country = country,
    city = city,
    district = district,
    registrationDate = registrationDate,
    friendsCount = friendsCount,
    subscriptionCount = subscriptionCount
)

fun List<SubscriberResponse>.toSubscriberList(): List<Subscriber> =
    this.map { it.toDomain() }