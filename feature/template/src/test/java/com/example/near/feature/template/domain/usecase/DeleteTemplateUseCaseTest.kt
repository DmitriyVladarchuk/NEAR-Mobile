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
import kotlin.test.assertTrue

class DeleteTemplateUseCaseTest {

    private lateinit var useCase: DeleteTemplateUseCase
    private val mockRepository = mockk<TemplateRepository>()
    private val testTemplate = Template(
        id = "template_123",
        templateName = "Test Template",
        message = "Test message content",
        emergencyType = emergencyTypes[0]
    )

    @Before
    fun setUp() {
        useCase = DeleteTemplateUseCase(mockRepository)
    }

    @Test
    fun `invoke should call repository deleteTemplate with correct template`() = runBlocking {
        coEvery { mockRepository.deleteTemplate(testTemplate) } returns Result.success(Unit)

        val result = useCase(testTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.deleteTemplate(testTemplate) }
    }

    @Test
    fun `invoke should handle different templates correctly`() = runBlocking {
        val anotherTemplate = testTemplate.copy(id = "template_456")
        coEvery { mockRepository.deleteTemplate(anotherTemplate) } returns Result.success(Unit)

        val result = useCase(anotherTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.deleteTemplate(anotherTemplate) }
    }
}