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

class UpdateTemplateUseCaseTest {

    private lateinit var useCase: UpdateTemplateUseCase
    private val mockRepository = mockk<TemplateRepository>()
    private val testTemplate = Template(
        id = "template_123",
        templateName = "Test Template",
        message = "Test message content",
        emergencyType = emergencyTypes[0]
    )

    @Before
    fun setUp() {
        useCase = UpdateTemplateUseCase(mockRepository)
    }

    @Test
    fun `invoke should call repository updateTemplate with correct template`() = runBlocking {
        coEvery { mockRepository.updateTemplate(testTemplate) } returns Result.success(Unit)

        val result = useCase(testTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.updateTemplate(testTemplate) }
    }

    @Test
    fun `invoke should handle template with different ID`() = runBlocking {
        val differentTemplate = testTemplate.copy(id = "template_456")
        coEvery { mockRepository.updateTemplate(differentTemplate) } returns Result.success(Unit)

        val result = useCase(differentTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.updateTemplate(differentTemplate) }
    }

    @Test
    fun `invoke should handle template with updated emergency type`() = runBlocking {
        val updatedTemplate = testTemplate.copy(emergencyType = emergencyTypes[1])
        coEvery { mockRepository.updateTemplate(updatedTemplate) } returns Result.success(Unit)

        val result = useCase(updatedTemplate)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.updateTemplate(updatedTemplate) }
    }
}