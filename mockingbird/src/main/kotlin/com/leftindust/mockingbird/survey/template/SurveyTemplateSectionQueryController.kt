package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateSectionQueryController(
    private val readSurveyTemplateSectionService: ReadSurveyTemplateSectionService,
    private val surveyTemplateSectionToSurveyTemplateSectionDtoConverter: InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto>,
) {
    @SchemaMapping(field = "sections", typeName = SurveyTemplateDto.GRAPHQL_TYPE_NAME)
    suspend fun surveyTemplateSection(surveyTemplateDto: SurveyTemplateDto): List<SurveyTemplateSectionDto> {
        val surveyTemplateSections = readSurveyTemplateSectionService.surveyTemplateSectionServiceBySurveySectionId(surveyTemplateDto.id)
            ?: throw NullSubQueryException(surveyTemplateDto, ReadSurveyTemplateSectionService::surveyTemplateSectionServiceBySurveySectionId)
        return surveyTemplateSections.map { surveyTemplateSectionToSurveyTemplateSectionDtoConverter.convert(it) }
    }
}