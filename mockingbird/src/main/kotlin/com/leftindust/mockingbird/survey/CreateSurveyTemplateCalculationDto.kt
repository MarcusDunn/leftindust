package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateCalculationDto (
    val label: String,
    val type: SurveyTemplateInputType,
    val showOnComplete: Boolean,
    val calculation: String,
)