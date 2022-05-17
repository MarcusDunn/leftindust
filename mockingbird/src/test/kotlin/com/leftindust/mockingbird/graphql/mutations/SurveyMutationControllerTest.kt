package com.leftindust.mockingbird.graphql.mutations

import com.google.gson.JsonParser
import com.leftindust.mockingbird.survey.CreateSurveyService
import com.leftindust.mockingbird.survey.FormDataDao
import com.leftindust.mockingbird.survey.Survey
import com.leftindust.mockingbird.survey.SurveyResponse
import com.leftindust.mockingbird.survey.SurveyMutationController
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.UpdatePatientService
import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SurveyMutationControllerTest {
    private val formDao = mockk<CreateSurveyService>()
    private val formDataDao = mockk<FormDataDao>()
    private val patientDao = mockk<UpdatePatientService>()

    @Test
    fun addForm() {
        val surveyMutationController = SurveyMutationController(formDao, formDataDao, patientDao)
        val graphQLFormTemplateInput = EntityStore.graphQLFormInput("FormMutationTest")

        val mockkSurvey = mockk<Survey>(relaxed = true)
        every { formDao.createSurvey(graphQLFormTemplateInput, any()) } returns mockkSurvey

        val result = runBlocking {
            surveyMutationController.addSurvey(graphQLFormTemplateInput, MockDataFetchingEnvironment.withDummyMediqToken)
        }

        assertEquals(SurveyDto(mockkSurvey), result)

        verify(exactly = 1) { formDao.createSurvey(graphQLFormTemplateInput, any()) }
    }

    @Test
    fun `test submitSurvey`() {
        val surveyMutationController = SurveyMutationController(formDao, formDataDao, patientDao)
        //language=Json
        val formString = """{"hello":"world"}"""
        val form = JsonParser.parseString(formString)

        val mockkGqlPatient = mockk<PatientDto>(relaxed = true)
        val mockkPatient = mockk<Patient>(relaxed = true)

        every { formDataDao.attachForm(mockkGqlPatient.pid, form) } returns SurveyResponse(form, mockkPatient)


        val result = runBlocking {
            surveyMutationController.submitSurvey(
                patient = mockkGqlPatient.pid,
                surveyJson = formString,
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }
        assertEquals(formString, result.data)
    }

    @Test
    fun `test assignSurvey`() {
        val surveyMutationController = SurveyMutationController(formDao, formDataDao, patientDao)

        val mockkPatientList = listOf(mockk<Patient>(relaxed = true))

        every { patientDao.assignForms(any(), any(), any()) } returns mockkPatientList

        val result = runBlocking {
            surveyMutationController.assignSurvey(
                patients = listOf(PatientDto.PatientDtoId(makeUUID())),
                survey = SurveyDto.SurveyDtoId(makeUUID()),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(mockkPatientList.map { PatientDto(it) }, result)
    }
}