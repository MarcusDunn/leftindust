package com.leftindust.mockingbird.survey

import java.util.UUID

interface SurveyTemplateSection {
    val subtitle: String?
    val title: String
    val id: UUID
}
