package com.leftindust.mockingbird.survey

interface CreateSurveyTemplateSection {
    val title: String
    val subtitle: String?
    val inputs: List<CreateSurveyTemplateSectionInput>
}