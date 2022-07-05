package com.leftindust.mockingbird.survey

data class CreateSurveyTemplateSectionInputDto (
    val id: Int,
    val type: SurveyTemplateInputType,
    val label: String,
    val options: List<String>?,
    val placeholder: String?,
    val required: Boolean,
    val category: SurveyTemplateCategory,
    val uploadMultiple: Boolean?,
    val uploadAccept: TemplateInputUploadType?,
)