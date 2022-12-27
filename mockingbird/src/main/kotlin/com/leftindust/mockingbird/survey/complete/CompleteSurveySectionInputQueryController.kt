package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.NullSubQueryException
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveySectionInputQueryController(
    private val readCompleteSurveySectionInputService: ReadCompleteSurveySectionInputService,
) {

    @SchemaMapping(field = "inputs", typeName = CompleteSurveySectionDto.GRAPHQL_TYPE)
    suspend fun completeSurveySectionInputByCompleteSurveySection(surveySectionDto: CompleteSurveySectionDto): List<CompleteSurveySectionInputDto> {
        val completeSurveySectionInputs =
            readCompleteSurveySectionInputService.completeSurveySectionInputByCompleteSurveySectionId(surveySectionDto.id)
                ?: throw NullSubQueryException(
                    surveySectionDto,
                    ReadCompleteSurveySectionInputService::completeSurveySectionInputByCompleteSurveySectionId
                )
        return completeSurveySectionInputs.map {
            it.toCompleteSurveySectionInputDto().onFailure { throw it.reason.toMockingbirdException() }
        }
    }
}
