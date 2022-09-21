package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter :
    InfallibleConverter<CompleteSurveySectionInputEntity, CompleteSurveySectionInput> {
    override fun convert(source: CompleteSurveySectionInputEntity): CompleteSurveySectionInput {
        return CompleteSurveySectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            value = when (source.value) {
                is JsonData.StringValue -> {
                    SurveySectionInput.StringInput(source.value.string)
                }
                is JsonData.StringArray -> {
                    SurveySectionInput.StringArrayInput(source.value.stringArray.map { it.string })
                }
                is JsonData.NumberValue -> {
                    SurveySectionInput.NumberInput(source.value.number)
                }
                is JsonData.NumberArray -> {
                    SurveySectionInput.NumberArrayInput(source.value.numberArray.map { it.number })
                }
            }
        )
    }

    class CompleteSurveySectionInputImpl(
        override val id: UUID,
        override val value: SurveySectionInput
    ) : CompleteSurveySectionInput {
    }
}