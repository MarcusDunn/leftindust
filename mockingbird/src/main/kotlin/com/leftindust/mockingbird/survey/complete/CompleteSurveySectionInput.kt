package com.leftindust.mockingbird.survey.complete

import java.util.UUID

interface CompleteSurveySectionInput {
    val id: UUID
    val value: CompleteSurveySectionInputValue
}

sealed interface CompleteSurveySectionInputValue

interface StringInput : CompleteSurveySectionInputValue {
    val string: String
}

interface NumberInput : CompleteSurveySectionInputValue {
    val number: Int
}

interface NumberArrayInput : CompleteSurveySectionInputValue {
    val numberArray: List<Int>
}

interface StringArrayInput : CompleteSurveySectionInputValue {
    val stringArray: List<String>
}
