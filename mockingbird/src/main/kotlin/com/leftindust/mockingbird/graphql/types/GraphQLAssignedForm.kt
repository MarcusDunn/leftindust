package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.AssignedForm
import java.util.*

@GraphQLName("AssignedSurvey")
data class GraphQLAssignedForm(
    val patient: GraphQLPatient,
    val surveyTemplate: GraphQLFormTemplate,
    val id: ID,
) {
    constructor(assignedSurvey: AssignedForm) : this(
        surveyTemplate = GraphQLFormTemplate(assignedSurvey.formTemplate),
        patient = GraphQLPatient(assignedSurvey.patient),
        id = ID(assignedSurvey.id!!),
    )

    @GraphQLName("AssignedSurveyId")
    data class ID(val id: UUID)
}
