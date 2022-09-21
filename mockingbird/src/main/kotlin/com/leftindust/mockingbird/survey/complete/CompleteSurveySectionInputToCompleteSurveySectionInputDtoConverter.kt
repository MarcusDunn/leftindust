package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter(
) : InfallibleConverter<CompleteSurveySectionInput, CompleteSurveySectionInputDto> {
    override fun convert(source: CompleteSurveySectionInput): CompleteSurveySectionInputDto {
        return when (val input = source.value) {
            is SurveySectionInput.StringInput -> {
                CompleteSurveySectionStringInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    string = input.string
                )
            }
            is SurveySectionInput.NumberInput -> {
                CompleteSurveySectionNumberInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    number = input.number
                )
            }
            is SurveySectionInput.StringArrayInput -> {
                CompleteSurveySectionStringArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    stringArray = input.stringArray
                )
            }
            is SurveySectionInput.NumberArrayInput -> {
                CompleteSurveySectionNumberArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    numberArray = input.numberArray
                )
            }
        }
    }
}