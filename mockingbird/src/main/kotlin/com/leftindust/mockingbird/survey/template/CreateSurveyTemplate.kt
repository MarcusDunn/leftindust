package com.leftindust.mockingbird.survey.template

interface CreateSurveyTemplate {
    val title: String
    val subtitle: String?
    val sections: List<CreateSurveyTemplateSection>
    val calculations: List<CreateSurveyTemplateCalculation>
}

