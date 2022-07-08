package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateDto (
    val title: String,
    val subtitle: String?,
    val sections: List<CreateSurveyTemplateSectionDto>,
    val calculations: List<CreateSurveyTemplateCalculationDto>,
)