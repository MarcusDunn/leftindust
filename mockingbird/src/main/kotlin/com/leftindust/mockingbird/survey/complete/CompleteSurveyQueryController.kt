package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveyQueryController(
    private val readCompleteSurveyService: ReadCompleteSurveyService,
) {

    @QueryMapping("completeSurveyById")
    suspend fun completeSurveyById(@Argument("completeSurveyId") completeSurveyDtoId: CompleteSurveyDtoId, env: DataFetchingEnvironment): CompleteSurveyDto? {
        val completeSurveyByCompleteSurveyId =
            readCompleteSurveyService.completeSurveyByCompleteSurveyId(completeSurveyDtoId)
                ?: return null
        return completeSurveyByCompleteSurveyId.toCompleteSurveyDto()
    }

    @QueryMapping("completeSurveyByRange")
    suspend fun completeSurveyByRange(@Argument("range") completeSurveyDtoIds: RangeDto): List<CompleteSurveyDto> {
        return readCompleteSurveyService.getMany(completeSurveyDtoIds).map { it.toCompleteSurveyDto() }
    }
}

