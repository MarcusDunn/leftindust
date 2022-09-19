package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveyQueryController(
    private val readCompleteSurveyService: ReadCompleteSurveyService,
    private val completeSurveyToCompleteSurveyDtoConverter: InfallibleConverter<CompleteSurvey, CompleteSurveyDto>
) {

    @QueryMapping("completeSurveyById")
    suspend fun completeSurveyById(@Argument("completeSurveyId") completeSurveyDtoId: CompleteSurveyDtoId): CompleteSurveyDto? {
        val completeSurveyByCompleteSurveyId =
            readCompleteSurveyService.completeSurveyByCompleteSurveyId(completeSurveyDtoId)
                ?: return null
        return completeSurveyToCompleteSurveyDtoConverter.convert(completeSurveyByCompleteSurveyId)
    }

    @QueryMapping("completeSurveyByRange")
    suspend fun completeSurveyByRange(@Argument("range") completeSurveyDtoIds: RangeDto): List<CompleteSurveyDto> {
        return readCompleteSurveyService.getMany(completeSurveyDtoIds).map { completeSurveyToCompleteSurveyDtoConverter.convert(it) }
    }

    @QueryMapping("completeSurveyByPatientId")
    suspend fun completeSurveyByPatientId(@Argument("patientId") patientId: PatientDto.PatientDtoId): CompleteSurveyDto? {
        val completeSurveyByCompleteSurveyId =
            readCompleteSurveyService.getByPatientId(patientId)
                ?: return null
        return completeSurveyToCompleteSurveyDtoConverter.convert(completeSurveyByCompleteSurveyId)
    }
}

