package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateTextSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateTextSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateTextSurveyField): SurveyFieldEntity {
        return SurveyFieldEntity(
            title = source.title,
            number = source.number,
            surveyFieldType = source.surveyFieldType,
            multiSelectPossibilities = null,
            intUpperBound = null,
            intLowerBound = null,
            floatUpperBound = null,
            floatLowerBound = null,
            dateUpperBound = null,
            dateLowerBound = null,
            textRegex = source.regex,
            jsonMetaData = null,
        )
    }
}