package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.survey.link.CreateSurveyLinkServiceImpl
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkRepository
import com.leftindust.mockingbird.survey.template.SurveyTemplateRepository
import com.leftindust.mockingbird.util.CompleteSurveyMother
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.SurveyLinkMother
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import dev.forkhandles.result4k.onFailure
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain

@ExtendWith(MockKExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class CreateCompleteSurveyImplUnitTest {
    @MockK
    private lateinit var completeSurveyRepository: CompleteSurveyRepository

    @MockK
    private lateinit var surveyLinkRepository: SurveyLinkRepository



    @Test
    internal fun `check saves a new entity`() = runTest {
        every { completeSurveyRepository.save(any()) } returns CompleteSurveyMother.FilledOutKoosKneeSurvey.entityDetached
        every { surveyLinkRepository.findByIdOrNull(any()) } returns SurveyLinkMother.KoosKneeSurveyLink.entityDetached
        val createCompleteSurveyService = CreateCompleteSurveyServiceImpl(
            completeSurveyRepository,
            surveyLinkRepository,

        )
        createCompleteSurveyService.createCompleteSurvey(CompleteSurveyMother.FilledOutKoosKneeSurvey.createDomain)
        verify(exactly = 1) { completeSurveyRepository.save(any()) }
    }
}

@DataJpaTest
@OptIn(ExperimentalCoroutinesApi::class)
internal class CreateCompleteSurveyImplDatabaseTest(
    @Autowired
    private val completeSurveyRepository: CompleteSurveyRepository,
    @Autowired
    private val surveyLinkRepository: SurveyLinkRepository,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockK
    private lateinit var surveyTemplateRepository: SurveyTemplateRepository

    @MockK
    private lateinit var patientRepository: PatientRepository


    @Test
    internal fun `check persists a new surveyTemplate`() = runTest {
        val createCompleteSurveyService = CreateCompleteSurveyServiceImpl(
            completeSurveyRepository,
            surveyLinkRepository,
        )
        val createSurveyLinkService = CreateSurveyLinkServiceImpl(
            surveyLinkRepository,
            surveyTemplateRepository,
            patientRepository,
        )

        every { surveyTemplateRepository.findByIdOrNull(any()) } returns SurveyTemplateMother.KoosKneeSurvey.entityDetached
        every { patientRepository.findByIdOrNull(any()) } returns PatientMother.Dan.entityDetached
        val createdSurveyLink = createSurveyLinkService.createSurveyLink(SurveyLinkMother.KoosKneeSurveyLink.createDto)
            ?: fail("could not create survey link")
        val newSurvey = object : CreateCompleteSurvey {
            override val surveyLinkId = SurveyLinkDto.SurveyLinkDtoId(createdSurveyLink.id)
            override val completeSurveyTemplateSections =
                CompleteSurveyMother.FilledOutKoosKneeSurvey.createCompleteSurveyTemplateSections
        }
        val completeSurvey =
            createCompleteSurveyService.createCompleteSurvey(newSurvey).onFailure { fail("failed to persist survey") }
        assertThat(completeSurveyRepository.findByIdOrNull(completeSurvey.id), notNullValue())
        assertThat(surveyLinkRepository.findByIdOrNull(createdSurveyLink.id), notNullValue())
        assertThat(surveyLinkRepository.findByIdOrNull(createdSurveyLink.id)?.completeSurvey, notNullValue())
    }
}
