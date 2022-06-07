package com.leftindust.mockingbird.survey


data class CreateSurveyDto(
    val name: String,
    val sections: List<CreateSurveySectionDto>,
)

