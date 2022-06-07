package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateSurveyServiceImpl(
    private val surveyRepository: HibernateSurveyRepository,
    private val createSurveyToSurveyConverter: InfallibleConverter<CreateSurvey, Survey>,
) : CreateSurveyService {
    override suspend fun createSurvey(form: CreateSurvey): Survey {
        val newSurvey = createSurveyToSurveyConverter.convert(form)
        return surveyRepository.save(newSurvey)
    }
}

