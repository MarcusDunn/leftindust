package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.EntityNotFoundException
import com.leftindust.mockingbird.MockingbirdException
import com.leftindust.mockingbird.survey.link.SurveyLink
import com.leftindust.mockingbird.survey.link.SurveyLinkRepository
import com.leftindust.mockingbird.survey.template.SurveyTemplateRepository
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.resultFrom
import dev.forkhandles.values.Value
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.PersistenceException

@Transactional
@Service
class CreateCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val surveyLinkRepository: SurveyLinkRepository,
    private val completeSurveyEntityToCompleteSurvey: CompleteSurveyEntityToCompleteSurvey,
) : CreateCompleteSurveyService {
    private val logger = KotlinLogging.logger { }
    override suspend fun createCompleteSurvey(createCompleteSurvey: CreateCompleteSurvey): Result4k<CompleteSurvey, MockingbirdException> {
        val newCompleteSurvey = CompleteSurveyEntity(
            sections = createCompleteSurvey.completeSurveyTemplateSections
                .map { createCompleteSurveySection ->
                    CompleteSurveySectionEntity(
                        inputs = createCompleteSurveySection.completedSurveyInputs
                            .map { createCompleteSurveyInput ->
                                CompleteSurveySectionInputEntity(
                                    value = createCompleteSurveyInput.value
                                )
                            }
                            .toSet()
                    )
                }
                .toSet(),
            surveyLink = run {
                surveyLinkRepository.findByIdOrNull(createCompleteSurvey.surveyLinkId.value)
                    ?: return Failure(
                        EntityNotFoundException(
                            createCompleteSurvey.surveyLinkId.value,
                            SurveyLink::class
                        )
                    )
                        .also { logger.debug { "Did not find a surveyTemplateLink with id [${createCompleteSurvey.surveyLinkId.value}] while creating $createCompleteSurvey" } }
            }
        )
        val completeSurveyEntity = completeSurveyRepository.save(newCompleteSurvey)
        return Success(completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity))
    }
}