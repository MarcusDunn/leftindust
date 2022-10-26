package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.link.CreateSurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import com.leftindust.mockingbird.survey.link.SurveyLinkEntityToSurveyLinkConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkToSurveyLinkDtoConverter
import com.leftindust.mockingbird.util.PatientMother.Dan
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import java.util.UUID

object SurveyLinkMother {
    val surveyLinkEntityToSurveyLinkConverter = SurveyLinkEntityToSurveyLinkConverter()
    val surveyLinkToSurveyLinkDtoConverter = SurveyLinkToSurveyLinkDtoConverter()

    object KoosKneeSurveyLink {
        val id = UUID.fromString("f765849b-cbb4-4226-ad39-6b7518237606")
        val graphqlId = SurveyLinkDto.SurveyLinkDtoId(id)
        val surveyTemplateEntityDetached = KoosKneeSurvey.entityDetached
        val surveyTemplateEntityTransient = KoosKneeSurvey.entityTransient
        val patientDetached = Dan.entityDetached
        val patientTransient = Dan.entityTransient

        val createDto = CreateSurveyLinkDto(
            patientId = Dan.graphqlId,
            surveyTemplateId = KoosKneeSurvey.graphqlId
        )


        val entityDetached: SurveyLinkEntity
            get() = SurveyLinkEntity(
                surveyTemplateEntity = surveyTemplateEntityDetached,
                patient = patientDetached,
                completeSurvey = null
            ).apply { id = this@KoosKneeSurveyLink.id }

        val entityTransient: SurveyLinkEntity
            get() = SurveyLinkEntity(
                surveyTemplateEntity = surveyTemplateEntityTransient,
                patient = patientTransient,
                completeSurvey = null
            )

        val domain = surveyLinkEntityToSurveyLinkConverter.convert(entityDetached)

        val dto = surveyLinkToSurveyLinkDtoConverter.convert(domain)
    }
}