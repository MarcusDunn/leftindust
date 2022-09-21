package com.leftindust.mockingbird.survey.complete

import java.util.UUID

interface CompleteSurveySection {
    val id: UUID
    val inputs: List<CompleteSurveySectionInput>
}
