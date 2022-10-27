package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.survey.link.ReadSurveyLinkService
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkToSurveyLinkDtoConverter
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientSurveyLinkQueryController(
    private val readSurveyLinkService: ReadSurveyLinkService,
    private val surveyLinkToSurveyLinkDtoConverter: SurveyLinkToSurveyLinkDtoConverter
) {

    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "assignedSurveys")
    suspend fun assignedSurveys(patientDto: PatientDto): List<SurveyLinkDto> {
        val surveyLinks = readSurveyLinkService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadPhoneService::getByPatientId)
        return surveyLinks.map { surveyLinkToSurveyLinkDtoConverter.convert(it) }
    }
}