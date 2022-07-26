package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateCalculationToSurveyTemplateCalculationDtoConverter : InfallibleConverter<SurveyTemplateCalculation, SurveyTemplateCalculationDto> {
    override fun convert(source: SurveyTemplateCalculation): SurveyTemplateCalculationDto {
        return SurveyTemplateCalculationDto(
            id = SurveyTemplateCalculationDto.SurveyTemplateCalculationDtoId(source.id),
            index = source.index,
            label = source.label,
            inputType = source.inputType,
            showOnComplete = source.showOnComplete,
            calculation = source.calculation,
        )
    }
}