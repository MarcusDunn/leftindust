package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.extensions.doThenNull
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveySectionServiceImpl(
    private val surveyRepository: HibernateSurveyRepository
) : ReadSurveySectionService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getBySurveyId(surveyId: SurveyDto.SurveyDtoId): Flow<SurveySection>? {
        return surveyRepository.findById(surveyId.value).orElse(null)?.sections?.asFlow()
            ?: doThenNull { logger.debug { "returning null from getBySurveyId for $surveyId" } }
    }
}