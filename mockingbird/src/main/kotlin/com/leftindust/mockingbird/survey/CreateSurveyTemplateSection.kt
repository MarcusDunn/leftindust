package com.leftindust.mockingbird.survey

interface CreateSurveyTemplateSection {
    val id: Int
    val title: String
    val subtitle: String?
    val inputs: List<CreateSurveyTemplateSectionInput>
}