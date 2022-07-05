package com.leftindust.mockingbird.survey

interface CreateSurveyTemplateSectionInput {
    val id: Int
    val label: String
    val required: Boolean
    val restriction: CreateSurveyTemplateSectionInputRestriction
}