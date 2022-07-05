package com.leftindust.mockingbird.survey

interface CreateSurveyTemplateSectionInput {
    val label: String
    val required: Boolean
    val restriction: CreateSurveyTemplateSectionInputRestriction
}