package com.leftindust.mockingbird.graphql.mutations

import com.google.gson.JsonParser
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.CreateFormDao
import com.leftindust.mockingbird.dao.FormDataDao
import com.leftindust.mockingbird.dao.entity.Form
import com.leftindust.mockingbird.dao.entity.FormData
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FormMutationTest {
    private val formDao = mockk<CreateFormDao>()
    private val formDataDao = mockk<FormDataDao>()
    private val patientDao = mockk<UpdatePatientDao>()

    @Test
    fun addForm() {
        val formMutation = FormMutation(formDao, formDataDao, patientDao)
        val graphQLFormTemplateInput = EntityStore.graphQLFormInput("FormMutationTest")

        val authContext = mockk<GraphQLAuthContext>(relaxed = true)

        val mockkForm = mockk<Form>(relaxed = true)
        every { formDao.addForm(graphQLFormTemplateInput, authContext.mediqAuthToken) } returns mockkForm

        val result = runBlocking { formMutation.addSurveyTemplate(graphQLFormTemplateInput, authContext = authContext) }

        assertEquals(GraphQLFormTemplate(mockkForm, authContext), result)

        verify(exactly = 1) { formDao.addForm(graphQLFormTemplateInput, authContext.mediqAuthToken) }
    }

    @Test
    fun `test submitSurvey`() {
        val formMutation = FormMutation(formDao, formDataDao, patientDao)
        //language=Json
        val formString = """{"hello":"world"}"""
        val form = JsonParser.parseString(formString)

        val mockkGqlPatient = mockk<GraphQLPatient>(relaxed = true)
        val mockkPatient = mockk<Patient>(relaxed = true)

        every { formDataDao.attachForm(mockkGqlPatient.pid, form, any()) } returns FormData(form, mockkPatient)

        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val result = runBlocking {
            formMutation.submitSurvey(
                mockkGqlPatient.pid,
                surveyJson = formString,
                authContext = authContext
            )
        }
        assertEquals(formString, result.data)
    }

    @Test
    fun `test assignSurvey`() {
        val formMutation = FormMutation(formDao, formDataDao, patientDao)

        val mockkPatientList = listOf(mockk<Patient>(relaxed = true))
        val mockkAuthContext = mockk<GraphQLAuthContext>(relaxed = true)

        every { patientDao.assignForms(any(), any(), any()) } returns mockkPatientList

        val result = runBlocking {
            formMutation.assignSurvey(
                listOf(GraphQLPatient.ID(makeUUID())),
                GraphQLFormTemplate.ID(makeUUID()),
                mockkAuthContext
            )
        }

        assertEquals(mockkPatientList.map { GraphQLPatient(it, mockkAuthContext) }, result)
    }
}