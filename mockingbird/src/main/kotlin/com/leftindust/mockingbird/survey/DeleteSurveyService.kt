package com.leftindust.mockingbird.survey

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('DELETE_SURVEY')")
interface DeleteSurveyService {
    suspend fun deleteById(form: SurveyDto.SurveyDtoId): Survey?
}