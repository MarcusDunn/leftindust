package com.leftindust.mockingbird.dao.impl

import com.google.gson.JsonObject
import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.survey.SurveyResponse
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.survey.HibernateSurveyResponseRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.survey.ReadSurveyResponseServiceImpl
import com.leftindust.mockingbird.patient.PatientDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class SurveyResponseDaoImplTest {
    private val authorizer = mockk<Authorizer> {
        coEvery { getAuthorization(any(), any()) } returns Authorization.Allowed
    }

    @Test
    fun `check attachForm success`(): Unit = runBlocking {
        val surveyResponse = mockk<SurveyResponse>()
        val formDataRepository = mockk<HibernateSurveyResponseRepository> {
            every { save(any()) } returns surveyResponse
        }
        val uuid = UUID.randomUUID()
        val patient = mockk<Patient>()
        val patientRepository = mockk<HibernatePatientRepository> {
            every { getById(uuid) } returns patient
        }

        val formDataDao = ReadSurveyResponseServiceImpl(formDataRepository, patientRepository, authorizer)
        val result = formDataDao.attachForm(PatientDto.PatientDtoId(uuid), JsonObject(), mockk())
        assertEquals(surveyResponse, result)
    }

    @Test
    fun `check getForms success`(): Unit = runBlocking {
        val uuid = UUID.randomUUID()
        val surveyResponse = mockk<SurveyResponse>()
        val formDataRepository = mockk<HibernateSurveyResponseRepository> {
            every { getByPatient_Id(uuid) } returns listOf(surveyResponse)
        }

        val formDataDao = ReadSurveyResponseServiceImpl(formDataRepository, mockk(), authorizer)
        val result = formDataDao.getForms(PatientDto.PatientDtoId(uuid), mockk())
        assertEquals(listOf(surveyResponse), result)
    }
}