package com.leftindust.mockingbird.survey.template

interface CreateSurveyTemplateSection {
    val title: String
    val subtitle: String?
    val inputs: List<CreateSurveyTemplateSectionInput>
    val calculationId: Int
}