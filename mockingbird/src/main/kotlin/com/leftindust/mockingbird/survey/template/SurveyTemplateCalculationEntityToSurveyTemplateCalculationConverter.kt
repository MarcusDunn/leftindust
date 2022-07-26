package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateCalculationEntityToSurveyTemplateCalculationConverter : InfallibleConverter<SurveyTemplateCalculationEntity, SurveyTemplateCalculation> {
    override fun convert(source: SurveyTemplateCalculationEntity): SurveyTemplateCalculation {
        return SurveyTemplateCalculationImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            index = source.index,
            label = source.label,
            inputType = source.inputType,
            showOnComplete = source.showOnComplete,
            calculation = source.calculation,
        )
    }

    private data class SurveyTemplateCalculationImpl(
        override val id: UUID,
        override val index: Int,
        override val label: String,
        override val inputType: SurveyTemplateInputType,
        override val showOnComplete: Boolean,
        override val calculation: String,
    ) : SurveyTemplateCalculation
}