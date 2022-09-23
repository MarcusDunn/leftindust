package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonSubTypes(
    value = [
        JsonSubTypes.Type(
            CompleteSurveySectionInputData.StringValue::class
        ),
        JsonSubTypes.Type(
            CompleteSurveySectionInputData.NumberValue::class
        ),
        JsonSubTypes.Type(
            CompleteSurveySectionInputData.StringArray::class
        ),
        JsonSubTypes.Type(
            CompleteSurveySectionInputData.NumberArray::class
        )
    ]
)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
sealed interface CompleteSurveySectionInputData {
    data class StringValue(
        val string: String
    ) : CompleteSurveySectionInputData

    data class NumberValue(
        val number: Int
    ) : CompleteSurveySectionInputData
    data class StringArray(
        val stringArray: List<StringValue>
    ) : CompleteSurveySectionInputData

    data class NumberArray(
        val numberArray: List<NumberValue>
    ) : CompleteSurveySectionInputData
}
