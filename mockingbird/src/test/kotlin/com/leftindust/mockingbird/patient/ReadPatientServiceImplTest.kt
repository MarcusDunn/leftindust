package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.survey.link.SurveyLinkRepository
import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class ReadPatientServiceImplJpaTest(
    @Autowired private val patientRepository: PatientRepository,
    @Autowired private val surveyLinkRepository: SurveyLinkRepository,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain
    
    private val readPatientServiceImpl = ReadPatientServiceImpl(patientRepository, surveyLinkRepository)

    @Test
    internal fun `recreate issue 145`() = runTest {
        patientRepository.save(PatientMother.Dan.entityTransient)

        val patients = readPatientServiceImpl.getMany(object : Range {
            override val from = 0
            override val to = 1
        })

        assertThat(patients, hasSize(equalTo(1)))
    }
}