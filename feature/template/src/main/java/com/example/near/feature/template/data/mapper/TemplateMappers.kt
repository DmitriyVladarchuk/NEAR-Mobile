package com.example.near.feature.template.data.mapper

import com.example.near.core.network.model.TemplateActionRequest
import com.example.near.core.network.model.TemplateCreateRequest
import com.example.near.core.network.model.TemplateSendRequest
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.model.Template

fun CreateTemplate.toRequest(): TemplateCreateRequest = TemplateCreateRequest(
    templateName = templateName,
    message = message,
    emergencyType = emergencyType
)

fun Template.toRequest(): TemplateActionRequest = TemplateActionRequest(
    templateId = id,
    templateName = templateName,
    message = message,
    emergencyType = emergencyType
)

fun SendTemplateParams.toRequest(): TemplateSendRequest = TemplateSendRequest(
    templateId = templateId,
    recipients = recipients
)