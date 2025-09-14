package com.example.near.feature.template.data

import com.example.near.core.network.AuthTokens
import com.example.near.core.network.SessionManager
import com.example.near.core.network.model.emergencyTypes
import com.example.near.core.network.service.CommunityService
import com.example.near.core.network.service.UserService
import com.example.near.core.network.util.NetworkUtils
import com.example.near.feature.template.data.repository.TemplateRepositoryImpl
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.model.Template
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TemplateRepositoryImplTest {

    private lateinit var repository: TemplateRepositoryImpl
    private val mockUserService = mockk<UserService>()
    private val mockCommunityService = mockk<CommunityService>()
    private val mockSessionManager = mockk<SessionManager>()

    private val testEmergencyType = emergencyTypes[0]
    private val testAuthToken = AuthTokens(
        accessToken = "test_token",
        refreshToken = "refresh_token",
        uuid = null
    )

    private val testCreateTemplate = CreateTemplate(
        templateName = "Test Template",
        message = "Test message content",
        emergencyType = testEmergencyType
    )

    private val testTemplate = Template(
        id = "template_123",
        templateName = "Test Template",
        message = "Test message content",
        emergencyType = testEmergencyType
    )

    private val testSendParams = SendTemplateParams(
        templateId = "template_123",
        recipients = listOf("user_123", "user_456")
    )

    @Before
    fun setUp() {
        repository = TemplateRepositoryImpl(
            userService = mockUserService,
            communityService = mockCommunityService,
            sessionManager = mockSessionManager
        )

        every { mockSessionManager.authToken } returns testAuthToken
        mockkObject(NetworkUtils)
    }

    @After
    fun tearDown() {
        unmockkObject(NetworkUtils)
    }

    @Test
    fun `createTemplate should return failure when network call fails`() = runBlocking {
        every { mockSessionManager.isCommunity } returns false

        val exception = RuntimeException("Network error")
        coEvery {
            NetworkUtils.executeVoidApiCall(any())
        } returns Result.failure(exception)

        val result = repository.createTemplate(testCreateTemplate)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `updateTemplate should return failure when network call fails`() = runBlocking {
        every { mockSessionManager.isCommunity } returns false

        val exception = RuntimeException("Network error")
        coEvery {
            NetworkUtils.executeVoidApiCall(any())
        } returns Result.failure(exception)

        val result = repository.updateTemplate(testTemplate)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteTemplate should return failure when network call fails`() = runBlocking {
        every { mockSessionManager.isCommunity } returns true

        val exception = RuntimeException("Network error")
        coEvery {
            NetworkUtils.executeVoidApiCall(any())
        } returns Result.failure(exception)

        val result = repository.deleteTemplate(testTemplate)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `sendTemplate should return failure when network call fails`() = runBlocking {
        every { mockSessionManager.isCommunity } returns false

        val exception = RuntimeException("Network error")
        coEvery {
            NetworkUtils.executeVoidApiCall(any())
        } returns Result.failure(exception)

        val result = repository.sendTemplate(testSendParams)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}