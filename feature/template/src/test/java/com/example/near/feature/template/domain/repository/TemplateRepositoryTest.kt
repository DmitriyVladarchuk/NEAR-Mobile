package com.example.near.feature.template.domain.repository

import com.example.near.core.network.model.emergencyTypes
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.model.Template
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TemplateRepositoryTest {

    private lateinit var repository: TemplateRepository
    private val mockRepository = mockk<TemplateRepository>()
    private val testEmergencyType = emergencyTypes[0]
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
        repository = mockRepository
    }

    @Test
    fun `createTemplate should return success result with correct parameters`() = runBlocking {
        coEvery { repository.createTemplate(testCreateTemplate) } returns Result.success(Unit)

        val result = repository.createTemplate(testCreateTemplate)

        assertTrue(result.isSuccess)
        coVerify { repository.createTemplate(testCreateTemplate) }
    }

    @Test
    fun `createTemplate should return failure result when repository fails`() = runBlocking {
        val exception = RuntimeException("Network error")
        coEvery { repository.createTemplate(testCreateTemplate) } returns Result.failure(exception)

        val result = repository.createTemplate(testCreateTemplate)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { repository.createTemplate(testCreateTemplate) }
    }

    @Test
    fun `updateTemplate should return success result with correct template`() = runBlocking {
        coEvery { repository.updateTemplate(testTemplate) } returns Result.success(Unit)

        val result = repository.updateTemplate(testTemplate)

        assertTrue(result.isSuccess)
        coVerify { repository.updateTemplate(testTemplate) }
    }

    @Test
    fun `deleteTemplate should return success result with correct template`() = runBlocking {
        coEvery { repository.deleteTemplate(testTemplate) } returns Result.success(Unit)

        val result = repository.deleteTemplate(testTemplate)

        assertTrue(result.isSuccess)
        coVerify { repository.deleteTemplate(testTemplate) }
    }

    @Test
    fun `sendTemplate should return success result with correct parameters`() = runBlocking {
        coEvery { repository.sendTemplate(testSendParams) } returns Result.success(Unit)

        val result = repository.sendTemplate(testSendParams)

        assertTrue(result.isSuccess)
        coVerify { repository.sendTemplate(testSendParams) }
    }

    @Test
    fun `sendTemplate should handle empty recipients list`() = runBlocking {
        val paramsWithEmptyRecipients = testSendParams.copy(recipients = emptyList())
        coEvery { repository.sendTemplate(paramsWithEmptyRecipients) } returns Result.success(Unit)

        val result = repository.sendTemplate(paramsWithEmptyRecipients)

        assertTrue(result.isSuccess)
        coVerify { repository.sendTemplate(paramsWithEmptyRecipients) }
    }
}