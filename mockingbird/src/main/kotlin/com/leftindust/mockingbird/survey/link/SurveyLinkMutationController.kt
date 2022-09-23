package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkMutationController(
    private val createSurveyLinkService: CreateSurveyLinkService,
    private val surveyLinkToSurveyLinkDtoConverter: InfallibleConverter<SurveyLink, SurveyLinkDto>,
) {
    private val logger = KotlinLogging.logger { }

    @MutationMapping("createSurveyLink")
    suspend fun createSurveyLink(@Argument("createSurveyLink") createSurveyLinkDto: CreateSurveyLinkDto): SurveyLinkDto? {
        val surveyLink = createSurveyLinkService.createSurveyLink(createSurveyLinkDto)
            ?: return null.also { logger.debug { "Failed to create surveyLink. Returning null" } }
        return surveyLinkToSurveyLinkDtoConverter.convert(surveyLink)
    }
}

data class CreateSurveyLinkDto(
    override val patientId: PatientDto.PatientDtoId,
    override val surveyTemplateId: SurveyTemplateDtoId,
) : CreateSurveyLink

interface CreateSurveyLink {
    val patientId: PatientDto.PatientDtoId
    val surveyTemplateId: SurveyTemplateDtoId
}