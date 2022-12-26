package com.leftindust.mockingbird.survey.complete


fun CompleteSurveySectionInput.toCompleteSurveySectionInputDto(): CompleteSurveySectionInputDto {
    return when (val input = value) {
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

}