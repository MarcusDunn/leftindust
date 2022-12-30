package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.UUID


fun CompleteSurveySectionInputEntity.toCompleteSurveySectionInput(): Result4k<CompleteSurveySectionInput, ConversionError<CompleteSurveySectionInputEntity, CompleteSurveySectionInput>> {
    return Success(
            CompleteSurveySectionInputImpl(
                    id = id ?: throw NullEntityIdInConverterException(this),
                    value = when (value) {
                        is CompleteSurveySectionInputData.StringValue -> {
                            StringInputImpl(value.string)
                        }

                        is CompleteSurveySectionInputData.StringArray -> {
                            StringArrayInputImpl(value.stringArray.map { it.string })
                        }

                        is CompleteSurveySectionInputData.NumberValue -> {
                            NumberInputImpl(value.number)
                        }

                        is CompleteSurveySectionInputData.NumberArray -> {
                            NumberArrayInputImpl(value.numberArray.map { it.number })
                        }
                    }
            )
    )
}


private class StringInputImpl(override val string: String) :
        StringInput {
    companion object {
        operator fun invoke(string: String?): StringInputImpl? {
            return if (string != null) StringInputImpl(string) else null
        }
    }
}

private class NumberInputImpl(override val number: Int) :
        NumberInput {
    companion object {
        operator fun invoke(number: Int?): NumberInputImpl? {
            return if (number != null) NumberInputImpl(number) else null
        }
    }
}

private class StringArrayInputImpl(override val stringArray: List<String>) :
        StringArrayInput {
    companion object {
        operator fun invoke(stringArray: List<String>?): StringArrayInputImpl? {
            return if (stringArray != null) StringArrayInputImpl(stringArray) else null
        }
    }
}

private class NumberArrayInputImpl(override val numberArray: List<Int>) :
        NumberArrayInput {
    companion object {
        operator fun invoke(numberArray: List<Int>?): NumberArrayInputImpl? {
            return if (numberArray != null) NumberArrayInputImpl(numberArray) else null
        }
    }
}

class CompleteSurveySectionInputImpl(
        override val id: UUID,
        override val value: CompleteSurveySectionInputValue
) : CompleteSurveySectionInput {
}


