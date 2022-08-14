package com.leftindust.mockingbird.survey.template

data class CreateSurveyTemplateSectionDto (
    val title: String,
    val subtitle: String?,
    val inputs: List<CreateSurveyTemplateSectionInputDto>,
    val calculationId: Int?
)