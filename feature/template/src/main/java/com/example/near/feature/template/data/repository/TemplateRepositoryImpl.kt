package com.example.near.feature.template.data.repository

import com.example.near.core.network.SessionManager
import com.example.near.core.network.service.CommunityService
import com.example.near.core.network.service.UserService
import com.example.near.core.network.util.NetworkUtils.executeApiCall
import com.example.near.core.network.util.NetworkUtils.executeVoidApiCall
import com.example.near.feature.template.data.mapper.toDomain
import com.example.near.feature.template.data.mapper.toRequest
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository

class TemplateRepositoryImpl(
    private val userService: UserService,
    private val communityService: CommunityService,
    private val sessionManager: SessionManager,
) : TemplateRepository {

    override suspend fun getTemplates(): Result<List<Template>> {
        val authHeader = "Bearer ${sessionManager.authToken!!.accessToken}"

        return if (sessionManager.isCommunity) {
            executeApiCall {
                communityService.getCommunityInfo(token = authHeader)
            }.map { response ->
                response.notificationTemplates.map { it.toDomain() }
            }
        } else {
            executeApiCall {
                userService.getUserInfo(token = authHeader)
            }.map { response ->
                response.notificationTemplates.map { it.toDomain() }
            }
        }
    }

    override suspend fun createTemplate(template: CreateTemplate): Result<Unit> {
        return executeVoidApiCall {
            val authHeader = "Bearer ${sessionManager.authToken!!.accessToken}"
            val request = template.toRequest()

            if (sessionManager.isCommunity) {
                communityService.createTemplate(token = authHeader, request = request)
            } else {
                userService.createTemplate(token = authHeader, request = request)
            }
        }
    }

    override suspend fun updateTemplate(template: Template): Result<Unit> {
        return executeVoidApiCall {
            val authHeader = "Bearer ${sessionManager.authToken!!.accessToken}"
            val request = template.toRequest()

            if (sessionManager.isCommunity) {
                communityService.updateTemplate(token = authHeader, request = request)
            } else {
                userService.updateTemplate(token = authHeader, request = request)
            }
        }
    }

    override suspend fun deleteTemplate(template: Template): Result<Unit> {
        return executeVoidApiCall {
            val authHeader = "Bearer ${sessionManager.authToken!!.accessToken}"
            val request = template.toRequest()

            if (sessionManager.isCommunity) {
                communityService.deleteTemplate(token = authHeader, request = request)
            } else {
                userService.deleteTemplate(token = authHeader, request = request)
            }
        }
    }

    override suspend fun sendTemplate(params: SendTemplateParams): Result<Unit> {
        return executeVoidApiCall {
            val authHeader = "Bearer ${sessionManager.authToken!!.accessToken}"
            val request = params.toRequest()

            if (sessionManager.isCommunity) {
                communityService.sendTemplate(token = authHeader, request = request)
            } else {
                userService.sendTemplate(token = authHeader, request = request)
            }
        }
    }
}