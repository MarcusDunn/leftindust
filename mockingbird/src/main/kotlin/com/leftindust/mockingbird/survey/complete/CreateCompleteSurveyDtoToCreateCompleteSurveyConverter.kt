package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateCompleteSurveyDtoToCreateCompleteSurveyConverter: InfallibleConverter<CreateCompleteSurveyDto, CreateCompleteSurvey> {
    override fun convert(source: CreateCompleteSurveyDto): CreateCompleteSurvey {
        return source
    }
}