package com.leftindust.mockingbird.form

import com.expediagroup.graphql.server.operations.Mutation
import com.google.gson.JsonParser.parseString
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.patient.UpdatePatientDao
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
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
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLFormTemplate = withContext(Dispatchers.IO) {
        createFormDao.addForm(surveyTemplate, dataFetchingEnvironment.authToken)
    }.let(::GraphQLFormTemplate)

    suspend fun submitSurvey(
        patient: GraphQLPatient.ID,
        surveyJson: String,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLFormData = withContext(Dispatchers.IO) {
        formDataDao.attachForm(patient, form = parseString(surveyJson), dataFetchingEnvironment.authToken)
    }.let { GraphQLFormData(it.data.toString(), patient) }

    suspend fun assignSurvey(
        patients: List<GraphQLPatient.ID>,
        survey: GraphQLFormTemplate.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.assignForms(patients, survey, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPatient)
}

