package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter :
    InfallibleConverter<CreateSurveyTemplateCalculationDto, CreateSurveyTemplateCalculation> {
    override fun convert(source: CreateSurveyTemplateCalculationDto): CreateSurveyTemplateCalculation {
        return CreateSurveyTemplateCalculationImpl(
            label = source.label,
            type = source.inputType,
            showOnComplete = source.showOnComplete,
            calculation = source.calculation,
        )
    }

    private data class CreateSurveyTemplateCalculationImpl(
        override val label: String,
        override val type: SurveyTemplateInputType,
        override val showOnComplete: Boolean,
        override val calculation: String
    ) : CreateSurveyTemplateCalculation
}