package com.leftindust.mockingbird.survey.template

import java.util.UUID

interface SurveyTemplateSection {
    val subtitle: String?
    val title: String
    val id: UUID
    val calculationId: Int?
}
