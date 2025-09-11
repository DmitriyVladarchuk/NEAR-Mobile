package com.example.near.feature.template.domain.usecase

import com.example.near.core.network.model.emergencyTypes
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.template.domain.repository.TemplateRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetTemplatesUseCaseTest {

    private lateinit var useCase: GetTemplatesUseCase
    private val mockRepository = mockk<TemplateRepository>()
    private val testEmergencyType = emergencyTypes[0]
    private val testTemplates = listOf(
        Template(
            id = "template_1",
            templateName = "Template 1",
            message = "Message 1",
            emergencyType = testEmergencyType
        ),
        Template(
            id = "template_2",
            templateName = "Template 2",
            message = "Message 2",
            emergencyType = testEmergencyType
        )
    )

    @Before
    fun setUp() {
        useCase = GetTemplatesUseCase(mockRepository)
    }

    @Test
    fun `invoke should return success result with templates list`() = runBlocking {
        coEvery { mockRepository.getTemplates() } returns Result.success(testTemplates)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(testTemplates, result.getOrNull())
        coVerify { mockRepository.getTemplates() }
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runBlocking {
        coEvery { mockRepository.getTemplates() } returns Result.success(emptyList())

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(emptyList(), result.getOrNull())
        coVerify { mockRepository.getTemplates() }
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {
        val exception = RuntimeException("Network error")
        coEvery { mockRepository.getTemplates() } returns Result.failure(exception)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { mockRepository.getTemplates() }
    }

    @Test
    fun `invoke should call repository exactly once`() = runBlocking {
        coEvery { mockRepository.getTemplates() } returns Result.success(testTemplates)

        useCase()

        coVerify(exactly = 1) { mockRepository.getTemplates() }
    }
}