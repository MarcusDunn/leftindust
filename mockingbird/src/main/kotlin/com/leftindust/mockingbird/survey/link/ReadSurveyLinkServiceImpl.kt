package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
    private val surveyLinkEntityToSurveyLinkConverter: InfallibleConverter<SurveyLinkEntity, SurveyLink>
) : ReadSurveyLinkService {
    override suspend fun surveyLinkBySurveyLinkId(surveyLinkId: SurveyLinkDto.SurveyLinkDtoId): SurveyLink? {
        val surveyLinkEntity = surveyLinkRepository.findByIdOrNull(surveyLinkId.value)
            ?: return null
        return surveyLinkEntityToSurveyLinkConverter.convert(surveyLinkEntity)
    }
}