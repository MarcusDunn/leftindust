package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.survey.link.ReadSurveyLinkService
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.toSurveyLinkDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientSurveyLinkQueryController(
    private val readSurveyLinkService: ReadSurveyLinkService,
) {

    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "assignedSurveys")
    suspend fun assignedSurveys(patientDto: PatientDto): List<SurveyLinkDto> {
        val surveyLinks = readSurveyLinkService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadPhoneService::getByPatientId)
        return surveyLinks.map { it.toSurveyLinkDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}