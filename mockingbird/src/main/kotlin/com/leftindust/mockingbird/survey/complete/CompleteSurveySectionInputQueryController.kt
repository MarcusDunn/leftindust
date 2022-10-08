package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class CompleteSurveySectionInputQueryController(
    private val readCompleteSurveySectionInputService: ReadCompleteSurveySectionInputService,
    private val completeSurveySectionInputToCompleteSurveySectionInputDtoConverter: CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter,
) {

    @SchemaMapping(field = "inputs", typeName = CompleteSurveySectionDto.GRAPHQL_TYPE)
    suspend fun completeSurveySectionInputByCompleteSurveySection(surveySectionDto: CompleteSurveySectionDto): List<CompleteSurveySectionInputDto> {
        val completeSurveySectionInputs = readCompleteSurveySectionInputService.completeSurveySectionInputByCompleteSurveySectionId(surveySectionDto.id)
            ?: throw NullSubQueryException(surveySectionDto, ReadCompleteSurveySectionInputService::completeSurveySectionInputByCompleteSurveySectionId)
        return completeSurveySectionInputs.map { completeSurveySectionInputToCompleteSurveySectionInputDtoConverter.convert(it) }
    }
}
