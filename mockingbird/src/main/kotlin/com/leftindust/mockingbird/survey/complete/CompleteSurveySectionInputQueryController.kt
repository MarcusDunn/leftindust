package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

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

@Component
class CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter : InfallibleConverter<CompleteSurveySectionInput, CompleteSurveySectionInputDto> {
    override fun convert(source: CompleteSurveySectionInput): CompleteSurveySectionInputDto {
        return CompleteSurveySectionInputDto(
            id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id)
        )
    }

}