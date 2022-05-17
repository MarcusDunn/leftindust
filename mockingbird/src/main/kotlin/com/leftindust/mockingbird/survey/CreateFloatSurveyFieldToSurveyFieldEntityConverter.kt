package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateFloatSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateFloatSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateFloatSurveyField): SurveyFieldEntity {
        return SurveyFieldEntity(
            title = source.title,
            number = source.number,
            surveyFieldType = source.surveyFieldType,
            multiSelectPossibilities = null,
            intUpperBound = null,
            intLowerBound = null,
            floatUpperBound = source.upperBound,
            floatLowerBound = source.lowerBound,
            dateUpperBound = null,
            dateLowerBound = null,
            textRegex = null,
            jsonMetaData = null,
        )
    }
}