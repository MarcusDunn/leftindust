package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateDateSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateDateSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateDateSurveyField): SurveyFieldEntity {
        return SurveyFieldEntity(
            title = source.title,
            number = source.number,
            surveyFieldType = source.surveyFieldType,
            multiSelectPossibilities = null,
            intUpperBound = null,
            intLowerBound = null,
            floatUpperBound = null,
            floatLowerBound = null,
            dateUpperBound = source.upperBound,
            dateLowerBound = source.lowerBound,
            textRegex = null,
            jsonMetaData = null,
        )
    }
}