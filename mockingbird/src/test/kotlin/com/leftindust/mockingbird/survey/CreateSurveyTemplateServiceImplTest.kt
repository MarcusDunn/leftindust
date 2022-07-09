package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain

@ExtendWith(MockKExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class CreateSurveyTemplateServiceImplUnitTest {
    @MockK
    private lateinit var surveyTemplateRepository: SurveyTemplateRepository

    private val surveyTemplateEntityToSurveyTemplateConverter = SurveyTemplateEntityToSurveyTemplateConverter()

    @Test
    internal fun `check saves a new entity`() = runTest {
        every { surveyTemplateRepository.save(any()) } returns SurveyTemplateMother.`koos knee survey template entity persisted`
        val createSurveyTemplateServiceImpl = CreateSurveyTemplateServiceImpl(surveyTemplateRepository, surveyTemplateEntityToSurveyTemplateConverter)
        createSurveyTemplateServiceImpl.createSurveyTemplate(SurveyTemplateMother.`create koos knee survey template`)
        verify(exactly = 1) { surveyTemplateRepository.save(any()) }
    }
}

@DataJpaTest
@OptIn(ExperimentalCoroutinesApi::class)
internal class CreateSurveyTemplateServiceImplDatabaseTest(
    @Autowired private val surveyTemplateRepository: SurveyTemplateRepository,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    private val createSurveyTemplateServiceImpl = CreateSurveyTemplateServiceImpl(surveyTemplateRepository, SurveyTemplateEntityToSurveyTemplateConverter())

    @Test
    internal fun `check persists a new surveyTemplate`() = runTest {
        val createSurveyTemplate = createSurveyTemplateServiceImpl.createSurveyTemplate(SurveyTemplateMother.`create koos knee survey template`)
        assertThat(surveyTemplateRepository.findByIdOrNull(createSurveyTemplate.id), notNullValue())
    }
}