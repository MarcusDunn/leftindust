package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateSectionDto (
    val title: String,
    val subtitle: String?,
    val inputs: List<CreateSurveyTemplateSectionInputDto>,
)