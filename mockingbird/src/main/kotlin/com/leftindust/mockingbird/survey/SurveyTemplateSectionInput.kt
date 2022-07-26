package com.leftindust.mockingbird.survey

import java.util.UUID

interface SurveyTemplateSectionInput {
    val id: UUID
    val label: String
    val category: SurveyTemplateCategory
    val restriction: SurveyTemplateSectionInputRestriction
    val required: Boolean
    val placeholder: String?
}

sealed interface SurveyTemplateSectionInputRestriction {

}

interface TextSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {

}

interface NumberSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {

}

interface DateSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {

}

interface ParagraphSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {

}

interface UploadSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {
    val uploadAccept: TemplateInputUploadType
    val uploadMultiple: Boolean
}

interface SingleSelectSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {
    val options: List<String>
}

interface MultiSelectSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {
    val options: List<String>
}

interface TitleSurveyTemplateSectionInputRestriction : SurveyTemplateSectionInputRestriction {

}