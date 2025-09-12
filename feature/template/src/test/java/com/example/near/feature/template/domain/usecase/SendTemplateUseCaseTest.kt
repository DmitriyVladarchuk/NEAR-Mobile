package com.example.near.feature.template.domain.usecase

import com.example.near.feature.template.domain.model.SendTemplateParams
import com.example.near.feature.template.domain.repository.TemplateRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class SendTemplateUseCaseTest {

    private lateinit var useCase: SendTemplateUseCase
    private val mockRepository = mockk<TemplateRepository>()
    private val testParams = SendTemplateParams(
        templateId = "template_123",
        recipients = listOf("user_123", "user_456")
    )

    @Before
    fun setUp() {
        useCase = SendTemplateUseCase(mockRepository)
    }

    @Test
    fun `invoke should call repository sendTemplate with correct parameters`() = runBlocking {
        coEvery { mockRepository.sendTemplate(testParams) } returns Result.success(Unit)

        val result = useCase(testParams)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.sendTemplate(testParams) }
    }

    @Test
    fun `invoke should handle single recipient`() = runBlocking {
        val singleRecipientParams = testParams.copy(recipients = listOf("user_789"))
        coEvery { mockRepository.sendTemplate(singleRecipientParams) } returns Result.success(Unit)

        val result = useCase(singleRecipientParams)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.sendTemplate(singleRecipientParams) }
    }

    @Test
    fun `invoke should handle empty recipients list`() = runBlocking {
        val emptyRecipientsParams = testParams.copy(recipients = emptyList())
        coEvery { mockRepository.sendTemplate(emptyRecipientsParams) } returns Result.success(Unit)

        val result = useCase(emptyRecipientsParams)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.sendTemplate(emptyRecipientsParams) }
    }

    @Test
    fun `invoke should handle different template IDs`() = runBlocking {
        val differentTemplateParams = testParams.copy(templateId = "template_456")
        coEvery { mockRepository.sendTemplate(differentTemplateParams) } returns Result.success(Unit)

        val result = useCase(differentTemplateParams)

        assertTrue(result.isSuccess)
        coVerify { mockRepository.sendTemplate(differentTemplateParams) }
    }
}