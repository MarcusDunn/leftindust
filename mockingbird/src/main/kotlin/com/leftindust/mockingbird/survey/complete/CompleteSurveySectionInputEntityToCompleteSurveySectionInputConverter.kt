package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter :
    InfallibleConverter<CompleteSurveySectionInputEntity, CompleteSurveySectionInput> {
    override fun convert(source: CompleteSurveySectionInputEntity): CompleteSurveySectionInput {
        source.value
        return CompleteSurveySectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            value = when (source.value) {
                is CompleteSurveySectionInputData.StringValue -> {
                    StringInputImpl(source.value.string)
                }
                is CompleteSurveySectionInputData.StringArray -> {
                    StringArrayInputImpl(source.value.stringArray.map { it.string })
                }
                is CompleteSurveySectionInputData.NumberValue -> {
                    NumberInputImpl(source.value.number)
                }
                is CompleteSurveySectionInputData.NumberArray -> {
                    NumberArrayInputImpl(source.value.numberArray.map { it.number })
                }
            }
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
}