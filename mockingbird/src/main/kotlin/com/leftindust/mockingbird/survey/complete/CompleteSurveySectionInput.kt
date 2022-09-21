package com.leftindust.mockingbird.survey.complete

import java.util.UUID

interface CompleteSurveySectionInput {
    val id: UUID
    val value: SurveySectionInput
}
