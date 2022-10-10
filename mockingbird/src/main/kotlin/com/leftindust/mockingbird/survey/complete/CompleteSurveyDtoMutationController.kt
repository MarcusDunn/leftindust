package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.get
import dev.forkhandles.result4k.onFailure
import dev.forkhandles.result4k.resultFrom
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import javax.persistence.PersistenceException

@Controller
class CompleteSurveyDtoMutationController(
    private val createCompleteSurveyService: CreateCompleteSurveyService,
    private val createCompleteSurveyDtoToCreateCompleteSurveyConverter: FallibleConverter<CreateCompleteSurveyDto, CreateCompleteSurvey>,
    private val completeSurveyToCompleteSurveyDtoConverter: InfallibleConverter<CompleteSurvey, CompleteSurveyDto>,
) {
    @MutationMapping("createCompleteSurvey")
    suspend fun createCompleteSurvey(@Argument("createCompleteSurvey") createCompleteSurveyDto: CreateCompleteSurveyDto): CompleteSurveyDto {
        val createCompleteSurvey = createCompleteSurveyDtoToCreateCompleteSurveyConverter.convert(createCompleteSurveyDto)
        val completeSurvey = createCompleteSurveyService.createCompleteSurvey(createCompleteSurvey ?: throw InconvertibleDtoException<CreateCompleteSurvey>(createCompleteSurveyDto)).onFailure { throw it.get() }
        return completeSurveyToCompleteSurveyDtoConverter.convert(completeSurvey)
    }
}

