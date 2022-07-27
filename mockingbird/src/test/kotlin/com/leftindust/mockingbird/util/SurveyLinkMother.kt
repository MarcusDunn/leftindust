package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import com.leftindust.mockingbird.survey.link.SurveyLinkEntityToSurveyLinkConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkToSurveyLinkDtoConverter
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import java.util.UUID

object SurveyLinkMother {
    val surveyLinkEntityToSurveyLinkConverter = SurveyLinkEntityToSurveyLinkConverter()
    val surveyLinkToSurveyLinkDtoConverter = SurveyLinkToSurveyLinkDtoConverter()

    object KoosKneeSurveyLink {
        val id = UUID.fromString("f765849b-cbb4-4226-ad39-6b7518237606")
        val graphqlId = SurveyLinkDto.SurveyLinkDtoId(id)
        val surveyTemplateEntity = KoosKneeSurvey.entityPersisted

        val entityPersisted = SurveyLinkEntity(
            surveyTemplateEntity = surveyTemplateEntity,
        ).apply { id = this@KoosKneeSurveyLink.id }

        val entityUnPersisted: SurveyLinkEntity = SurveyLinkEntity(
            surveyTemplateEntity = surveyTemplateEntity,
        ).apply { id = null }

        val domain = surveyLinkEntityToSurveyLinkConverter.convert(entityPersisted)

        val dto = surveyLinkToSurveyLinkDtoConverter.convert(domain)
    }
}