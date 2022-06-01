package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSurveyFieldToSurveyFieldEntity(
    private val createDateSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateDateSurveyField, SurveyFieldEntity>,
    private val createFloatSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateFloatSurveyField, SurveyFieldEntity>,
    private val createIntegerSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateIntegerSurveyField, SurveyFieldEntity>,
    private val createMultiMultiSelectSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateMultiMultiSelectSurveyField, SurveyFieldEntity>,
    private val createSingleMultiSelectSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateSingleMultiSelectSurveyField, SurveyFieldEntity>,
    private val createTextSurveyFieldToSurveyFieldEntityConverter: InfallibleConverter<CreateSurveyField.CreateTextSurveyField, SurveyFieldEntity>,
) : InfallibleConverter<CreateSurveyField, SurveyFieldEntity> {
    override fun convert(source: CreateSurveyField): SurveyFieldEntity {
        return when (source) {
            is CreateSurveyField.CreateDateSurveyField -> createDateSurveyFieldToSurveyFieldEntityConverter.convert(source)
            is CreateSurveyField.CreateFloatSurveyField -> createFloatSurveyFieldToSurveyFieldEntityConverter.convert(source)
            is CreateSurveyField.CreateIntegerSurveyField -> createIntegerSurveyFieldToSurveyFieldEntityConverter.convert(source)
            is CreateSurveyField.CreateMultiMultiSelectSurveyField -> createMultiMultiSelectSurveyFieldToSurveyFieldEntityConverter.convert(source)
            is CreateSurveyField.CreateSingleMultiSelectSurveyField -> createSingleMultiSelectSurveyFieldToSurveyFieldEntityConverter.convert(source)
            is CreateSurveyField.CreateTextSurveyField -> createTextSurveyFieldToSurveyFieldEntityConverter.convert(source)
        }
    }
}