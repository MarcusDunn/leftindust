package com.leftindust.mockingbird.survey

import java.util.UUID

interface SurveyTemplate {
    val id: UUID
    val title: String
    val subtitle: String?
    val sections: List<CreateSurveyTemplateSection>
    val calculations: List<CreateSurveyTemplateCalculation>
}
