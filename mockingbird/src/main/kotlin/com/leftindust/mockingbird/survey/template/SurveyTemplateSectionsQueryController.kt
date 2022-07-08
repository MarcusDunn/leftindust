package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateSectionsQueryController(
    private val readSurveyTemplateSectionService: ReadSurveyTemplateSectionService,
    private val surveyTemplateSectioneToSurveyTemplateSectionDtoConverter: InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto>
) {
    @SchemaMapping(typeName = SurveyTemplateDto.SCHEMA_TYPE_NAME, field = "sections")
    suspend fun surveyTemplateSections(surveyTemplateDto: SurveyTemplateDto): List<SurveyTemplateSectionDto> {
        val surveyTemplateSections = readSurveyTemplateSectionService.getSurveyTemplateSectionsBySurveyTemplateId(surveyTemplateDto.id)
        return surveyTemplateSections.map { surveyTemplateSectioneToSurveyTemplateSectionDtoConverter.convert(it) }
    }
}

