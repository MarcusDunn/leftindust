package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateSectionInputQueryController(
    private val readSurveyTemplateSectionInputService: ReadSurveyTemplateSectionInputService,
    private val surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter: InfallibleConverter<SurveyTemplateSectionInput, SurveyTemplateSectionInputDto>,
) {

    @SchemaMapping(field = "inputs", typeName = SurveyTemplateSectionDto.GRAPHQL_TYPE_NAME)
    suspend fun surveyTemplateDtoSectionInputs(surveyTemplateSectionDto: SurveyTemplateSectionDto): List<SurveyTemplateSectionInputDto> {
        val surveyTemplateSectionInputs = readSurveyTemplateSectionInputService.surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDto.id)
            ?: throw NullSubQueryException(surveyTemplateSectionDto, ReadSurveyTemplateSectionInputService::surveyTemplateSectionInputBySurveySection)
        return surveyTemplateSectionInputs.map { surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter.convert(it) }
    }
}