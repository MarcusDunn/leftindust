package com.leftindust.mockingbird.survey.complete

interface ReadCompleteSurveySectionInputService {
    suspend fun completeSurveySectionInputByCompleteSurveySectionId(completeSurveySectionDtoId: CompleteSurveySectionDto.CompleteSurveySectionDtoId): List<CompleteSurveySectionInput>?
}
