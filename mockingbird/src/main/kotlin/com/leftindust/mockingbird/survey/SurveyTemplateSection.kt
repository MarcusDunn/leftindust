package com.leftindust.mockingbird.survey

import java.util.UUID

interface SurveyTemplateSection {
    val id: UUID
    val title: String
    val subtitle: String?
}
