package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateMultiMultiSelectSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateMultiMultiSelectSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateMultiMultiSelectSurveyField): SurveyFieldEntity {
        return SurveyFieldEntity(
            title = source.title,
            number = source.number,
            surveyFieldType = source.surveyFieldType,
            multiSelectPossibilities = source.multiSelectPossibilities,
            intUpperBound = null,
            intLowerBound = null,
            floatUpperBound = null,
            floatLowerBound = null,
            dateUpperBound = null,
            dateLowerBound = null,
            textRegex = null,
            jsonMetaData = null,
        )
    }
}