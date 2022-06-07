package com.leftindust.mockingbird.survey

data class CreateSurveySectionDto(
    val name: String,
    val number: Int,
    val description: String,
    val fields: List<CreateSurveyFieldDto>,
)

