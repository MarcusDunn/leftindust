package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.AssignedForm
import java.util.*

@GraphQLName("AssignedSurvey")
data class GraphQLAssignedForm(
    val patient: GraphQLPatient,
    val surveyTemplate: GraphQLFormTemplate,
    val id: ID,
    private val authContact: GraphQLAuthContext,
) {
    constructor(assignedSurvey: AssignedForm, authContact: GraphQLAuthContext) : this(
        surveyTemplate = GraphQLFormTemplate(assignedSurvey.formTemplate, authContact),
        patient = GraphQLPatient(assignedSurvey.patient, authContact),
        id = ID(assignedSurvey.id!!),
        authContact = authContact
    )

    @GraphQLName("AssignedSurveyId")
    data class ID(val id: UUID)
}
