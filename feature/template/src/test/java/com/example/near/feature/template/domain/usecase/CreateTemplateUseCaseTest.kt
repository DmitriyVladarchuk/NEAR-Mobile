package com.example.near.feature.template.domain.usecase

import com.example.near.core.network.model.emergencyTypes
import com.example.near.feature.template.domain.model.CreateTemplate
import com.example.near.feature.template.domain.repository.TemplateRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CreateTemplateUseCaseTest {

    private lateinit var useCase: CreateTemplateUseCase
    private val mockRepository = mockk<TemplateRepository>()
    private val testEmergencyType = emergencyTypes[0]
    private val testCreateTemplate = CreateTemplate(
        templateName = "Test Template",
        message = "Test message content",
        emergencyType = testEmergencyType
    )

    @Before
    fun setUp() {
        useCase = CreateTemplateUseCase(mockRepository)
    }

    @Test
    fun `invoke should call repository createTemplate with correct parameters`() = runBlocking {
        coEvery { mockRepository.createTemplate(testCreateTemplate) } returns Result.success(Unit)

        val result = useCase(testCreateTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.createTemplate(testCreateTemplate) }
    }

    @Test
    fun `invoke should propagate success result from repository`() = runBlocking {
        coEvery { mockRepository.createTemplate(testCreateTemplate) } returns Result.success(Unit)

        val result = useCase(testCreateTemplate)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `invoke should propagate failure result from repository`() = runBlocking {
        val exception = RuntimeException("Database error")
        coEvery { mockRepository.createTemplate(testCreateTemplate) } returns Result.failure(exception)

        val result = useCase(testCreateTemplate)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `invoke should handle different emergency types correctly`() = runBlocking {
        val newTemplate = testCreateTemplate.copy(emergencyType = emergencyTypes[1])
        coEvery { mockRepository.createTemplate(newTemplate) } returns Result.success(Unit)

        val result = useCase(newTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.createTemplate(newTemplate) }
    }
}