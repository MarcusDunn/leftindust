package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto

data class CreateCompleteSurveyInputDto(
    val surveyTemplateSectionInputId: SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId,
    val type: SurveyInputType,
    val stringInput: String?,
    val numberInput: Int?,
    val stringArrayInput: List<String>?,
    val numberArrayInput: List<Int>?,
)

enum class SurveyInputType {
    String,
    Number,
    NumberArray,
    StringArray
}