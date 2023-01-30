package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.survey.template.SurveyTemplateRepository
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateSurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val patientRepository: PatientRepository,
) : CreateSurveyLinkService {
    private val logger = KotlinLogging.logger { }
    override suspend fun createSurveyLink(createSurveyLink: CreateSurveyLink): SurveyLink? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(createSurveyLink.surveyTemplateId.value)
            ?: return null.also { logger.debug { "Did not create survey link. Could not find a surveyTemplate with id ${createSurveyLink.surveyTemplateId}" } }

        val patient = patientRepository.findByIdOrNull(createSurveyLink.patientId.value)
            ?: return null.also { logger.debug { "Did not create survey link. Could not find a patient with id ${createSurveyLink.patientId}" } }

        val newSurveyLinkEntity = SurveyLinkEntity(
            surveyTemplateEntity = surveyTemplateEntity,
            patient = patient,
            completeSurvey = null
        )
        val surveyLinkEntity = surveyLinkRepository.save(newSurveyLinkEntity)
        return surveyLinkEntity.toSurveyLink().onFailure { throw it.reason.toMockingbirdException() }
    }
}

