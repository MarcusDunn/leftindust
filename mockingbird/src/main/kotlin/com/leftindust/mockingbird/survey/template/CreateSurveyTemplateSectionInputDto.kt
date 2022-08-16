package com.leftindust.mockingbird.survey.template

data class CreateSurveyTemplateSectionInputDto (
    val type: SurveyTemplateInputType,
    val label: String,
    val options: List<String>?,
    val placeholder: String?,
    val required: Boolean,
    val category: SurveyTemplateCategory,
    val uploadMultiple: Boolean?,
    val uploadAccept: TemplateInputUploadType?,
    val calculationId: Int
)