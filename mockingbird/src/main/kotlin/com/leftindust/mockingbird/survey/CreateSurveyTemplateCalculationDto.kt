package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateCalculationDto (
    val label: String,
    val inputType: SurveyTemplateInputType,
    val showOnComplete: Boolean,
    val calculation: String,
)