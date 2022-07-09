package com.leftindust.mockingbird.survey

interface CreateSurveyTemplateCalculation {
    val label: String
    val type: SurveyTemplateInputType
    val showOnComplete: Boolean
    val calculation: String
}