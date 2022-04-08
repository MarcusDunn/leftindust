package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.google.gson.JsonParser.parseString
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.CreateFormDao
import com.leftindust.mockingbird.dao.FormDataDao
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLFormData
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLFormTemplateInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class FormMutation(
    private val createFormDao: CreateFormDao,
    private val formDataDao: FormDataDao,
    private val patientDao: UpdatePatientDao
) : Mutation {
    suspend fun addSurveyTemplate(
        surveyTemplate: GraphQLFormTemplateInput,
        authContext: GraphQLAuthContext
    ): GraphQLFormTemplate = withContext(Dispatchers.IO) {
        createFormDao.addForm(surveyTemplate, authContext.mediqAuthToken)
    }.let { GraphQLFormTemplate(it, authContext) }

    suspend fun submitSurvey(
        patient: GraphQLPatient.ID,
        surveyJson: String,
        authContext: GraphQLAuthContext
    ): GraphQLFormData = withContext(Dispatchers.IO) {
        formDataDao.attachForm(patient, form = parseString(surveyJson), authContext.mediqAuthToken)
    }.let { GraphQLFormData(it.data.toString(), patient, authContext) }

    suspend fun assignSurvey(
        patients: List<GraphQLPatient.ID>,
        survey: GraphQLFormTemplate.ID,
        authContext: GraphQLAuthContext,
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.assignForms(patients, survey, authContext.mediqAuthToken)
    }.map { GraphQLPatient(it, authContext) }
}

