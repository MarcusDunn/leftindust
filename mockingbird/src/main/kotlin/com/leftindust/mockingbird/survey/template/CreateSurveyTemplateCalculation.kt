package com.leftindust.mockingbird.survey.template

interface CreateSurveyTemplateCalculation {
    val label: String
    val type: SurveyTemplateInputType
    val showOnComplete: Boolean
    val calculation: String
}