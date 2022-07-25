package com.leftindust.mockingbird.survey

import java.util.UUID

interface SurveyTemplateSectionInput {
    val id: UUID
    val uploadAccept: TemplateInputUploadType?
    val uploadMultiple: Boolean?
    val category: SurveyTemplateCategory
    val required: Boolean
    val placeholder: String?
    val options: List<String>?
    val label: String
    val type: SurveyTemplateInputType
}
