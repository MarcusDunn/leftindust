package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateSectionsQueryController(
    private val readSurveyTemplateService: ReadSurveyTemplateService,
    private val surveyTemplateSectioneToSurveyTemplateSectionDtoConverter: InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto>
) {
    @SchemaMapping(typeName = SurveyTemplateDto.SCHEMA_TYPE_NAME, field = "sections")
    suspend fun surveyTemplateSections(surveyTemplateDto: SurveyTemplateDto): List<SurveyTemplateSectionDto> {
        val surveyTemplateSections = readSurveyTemplateService.getSurveySections(surveyTemplateDto.id)
        return surveyTemplateSections.map { surveyTemplateSectioneToSurveyTemplateSectionDtoConverter.convert(it) }
    }
}

@Component
class SurveyTemplateSectionToSurveyTemplateSectionDtoConverter : InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto> {
    override fun convert(source: SurveyTemplateSection): SurveyTemplateSectionDto {
        return SurveyTemplateSectionDto(
            id = SurveyTemplateSectionDto.SurveyTemplateSectionDtoId(source.id),
            title = source.title,
            subtitle = source.subtitle,
        )
    }
}