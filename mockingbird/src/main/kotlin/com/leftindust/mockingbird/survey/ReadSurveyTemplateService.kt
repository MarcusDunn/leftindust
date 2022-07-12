package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_Doctor')")
interface ReadSurveyTemplateService {
    suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.Id): SurveyTemplateEntity?
    // we should be using SurveyTemplate class but that class only has an UUID.
}