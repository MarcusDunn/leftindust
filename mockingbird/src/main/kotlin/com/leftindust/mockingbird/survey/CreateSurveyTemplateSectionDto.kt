package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateSectionDto (
    val id: Int,
    val title: String,
    val subtitle: String?,
    val inputs: List<CreateSurveyTemplateSectionInputDto>,
)