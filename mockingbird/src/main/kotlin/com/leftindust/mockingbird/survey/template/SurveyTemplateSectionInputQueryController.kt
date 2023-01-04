package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.NullSubQueryException
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateSectionInputQueryController(
    private val readSurveyTemplateSectionInputService: ReadSurveyTemplateSectionInputService,
) {

    @SchemaMapping(field = "inputs", typeName = SurveyTemplateSectionDto.GRAPHQL_TYPE_NAME)
    suspend fun surveyTemplateDtoSectionInputs(surveyTemplateSectionDto: SurveyTemplateSectionDto): List<SurveyTemplateSectionInputDto> {
        val surveyTemplateSectionInputs =
            readSurveyTemplateSectionInputService.surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDto.id)
                ?: throw NullSubQueryException(
                    surveyTemplateSectionDto,
                    ReadSurveyTemplateSectionInputService::surveyTemplateSectionInputBySurveySection
                )
        return surveyTemplateSectionInputs.map {
            it.toSurveyTemplateSectionInputDto().onFailure { throw it.reason.toMockingbirdException() }
        }
    }
}