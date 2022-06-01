package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateIntegerSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateIntegerSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateIntegerSurveyField): SurveyFieldEntity {
        return SurveyFieldEntity(
            title = source.title,
            number = source.number,
            surveyFieldType = source.surveyFieldType,
            multiSelectPossibilities = null,
            intUpperBound = source.upperBound,
            intLowerBound = source.lowerBound,
            floatUpperBound = null,
            floatLowerBound = null,
            dateUpperBound = null,
            dateLowerBound = null,
            textRegex = null,
            jsonMetaData = null,
        )
    }
}