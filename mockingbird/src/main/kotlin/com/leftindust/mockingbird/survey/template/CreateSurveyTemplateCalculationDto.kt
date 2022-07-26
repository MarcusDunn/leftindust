package com.leftindust.mockingbird.survey.template

data class CreateSurveyTemplateCalculationDto (
    val label: String,
    val inputType: SurveyTemplateInputType,
    val showOnComplete: Boolean,
    val calculation: String,
)