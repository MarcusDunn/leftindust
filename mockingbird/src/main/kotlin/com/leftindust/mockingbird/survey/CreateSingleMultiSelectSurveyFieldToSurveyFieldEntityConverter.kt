package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSingleMultiSelectSurveyFieldToSurveyFieldEntityConverter : InfallibleConverter<CreateSurveyField.CreateSingleMultiSelectSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField.CreateSingleMultiSelectSurveyField): SurveyFieldEntity {
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