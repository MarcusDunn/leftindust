package com.leftindust.mockingbird.survey.template

import java.util.UUID

interface SurveyTemplateCalculation {
    val id: UUID
    val index: Int
    val label: String
    val inputType: SurveyTemplateInputType
    val showOnComplete: Boolean
    val calculation: String
}
