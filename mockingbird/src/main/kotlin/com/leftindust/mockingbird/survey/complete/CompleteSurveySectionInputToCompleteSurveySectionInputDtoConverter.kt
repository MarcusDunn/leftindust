package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter(
) : InfallibleConverter<CompleteSurveySectionInput, CompleteSurveySectionInputDto> {
    override fun convert(source: CompleteSurveySectionInput): CompleteSurveySectionInputDto {
        return when (val input = source.value) {
            is StringInput -> {
                CompleteSurveySectionStringInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    string = input.string
                )
            }
            is NumberInput -> {
                CompleteSurveySectionNumberInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    number = input.number
                )
            }
            is StringArrayInput -> {
                CompleteSurveySectionStringArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    stringArray = input.stringArray
                )
            }
            is NumberArrayInput -> {
                CompleteSurveySectionNumberArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
                    numberArray = input.numberArray
                )
            }
        }
    }
}