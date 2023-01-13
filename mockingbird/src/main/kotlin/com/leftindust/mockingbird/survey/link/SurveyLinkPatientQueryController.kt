package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.patient.toPatientDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkPatientQueryController(
    private val readPatientService: ReadPatientService,
) {

    @SchemaMapping("patient", typeName = SurveyLinkDto.GRAPHQL_TYPE)
    suspend fun surveyLinkPatient(surveyLinkDto: SurveyLinkDto): PatientDto {
        val patient = readPatientService.getBySurveyLink(surveyLinkDto.id)
            ?: throw NullSubQueryException(surveyLinkDto, ReadPatientService::getBySurveyLink)
        return patient.toPatientDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}