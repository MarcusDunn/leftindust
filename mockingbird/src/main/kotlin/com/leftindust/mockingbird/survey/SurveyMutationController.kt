package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
import mu.KotlinLogging
import org.springframework.stereotype.Controller

@Controller
class SurveyMutationController(
    private val createSurveyService: CreateSurveyService,
    private val createSurveyDtoToCreateSurveyConverter: FallibleConverter<CreateSurveyDto, CreateSurvey>,
    private val surveyToSurveyDtoConverter: InfallibleConverter<Survey, SurveyDto>,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun addSurvey(survey: CreateSurveyDto): SurveyDto? {
        val createSurvey = createSurveyDtoToCreateSurveyConverter.convert(survey)
            ?: return doThenNull { logger.debug { "returning null from addSurvey for $survey" } }
        val newSurvey = createSurveyService.createSurvey(createSurvey)
        return surveyToSurveyDtoConverter.convert(newSurvey)
    }

}

