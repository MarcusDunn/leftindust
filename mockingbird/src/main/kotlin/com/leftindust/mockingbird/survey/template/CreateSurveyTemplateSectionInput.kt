package com.leftindust.mockingbird.survey.template

interface CreateSurveyTemplateSectionInput {
    val label: String
    val required: Boolean
    val placeholder: String?
    val category: SurveyTemplateCategory
    val restriction: CreateSurveyTemplateSectionInputRestriction
}