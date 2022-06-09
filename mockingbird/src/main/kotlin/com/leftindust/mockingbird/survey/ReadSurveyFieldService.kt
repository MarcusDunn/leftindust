package com.leftindust.mockingbird.survey

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_')")
interface ReadSurveyFieldService {
    fun getBySurveySectionId(surveySectionId: SurveySectionDto.SurveySectionDtoId): List<SurveyFieldEntity>
}
