package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun CompleteSurveySectionInput.toCompleteSurveySectionInputDto(): Result4k<CompleteSurveySectionInputDto, ConversionError<CompleteSurveySectionInput, CompleteSurveySectionInputDto>> {
    return Success(
        when (val input = value) {
            is StringInput -> {
                CompleteSurveySectionStringInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(id),
                    string = input.string
                )
            }

            is NumberInput -> {
                CompleteSurveySectionNumberInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(id),
                    number = input.number
                )
            }

            is StringArrayInput -> {
                CompleteSurveySectionStringArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(id),
                    stringArray = input.stringArray
                )
            }

            is NumberArrayInput -> {
                CompleteSurveySectionNumberArrayInput(
                    id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(id),
                    numberArray = input.numberArray
                )
            }
        }
    )

}