package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID


@JsonTypeInfo(
    use = JsonTypeInfo.Id.DEDUCTION,
    include = JsonTypeInfo.As.PROPERTY,
    property = "__typename",
    defaultImpl = CompleteSurveySectionStringInput::class
)
@JsonSubTypes(
    value = [
        JsonSubTypes.Type(
            value = CompleteSurveySectionStringInput::class,
            name = "CompleteSurveySectionStringInput"
        ),
        JsonSubTypes.Type(
            value = CompleteSurveySectionNumberInput::class,
            name = "CompleteSurveySectionNumberInput"
        ),
        JsonSubTypes.Type(
            value = CompleteSurveySectionNumberArrayInput::class,
            name = "CompleteSurveySectionNumberArrayInput"
        ),
        JsonSubTypes.Type(
            value = CompleteSurveySectionStringArrayInput::class,
            name = "CompleteSurveySectionStringArrayInput"
        )
    ]
)
interface CompleteSurveySectionInputDto {
    val id: CompleteSurveySectionInputDtoId

    data class CompleteSurveySectionInputDtoId(override val value: UUID) : AbstractGraphQLDto.GraphQLID<UUID>

    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySectionInput"
    }
}

data class CompleteSurveySectionStringInput(
    override val id: CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId,
    val string: String
) : CompleteSurveySectionInputDto {

    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySectionStringInput"
    }
}

data class CompleteSurveySectionNumberInput(
    override val id: CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId,
    val number: Int
) : CompleteSurveySectionInputDto {
    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySectionNumberInput"
    }
}

data class CompleteSurveySectionNumberArrayInput(
    override val id: CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId,
    val numberArray: List<Int>
) : CompleteSurveySectionInputDto {
    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySectionNumberArrayInput"
    }
}

data class CompleteSurveySectionStringArrayInput(
    override val id: CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId,
    val stringArray: List<String>
) : CompleteSurveySectionInputDto {
    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySectionStringArrayInput"
    }
}


sealed class SurveySectionInput {
    data class StringInput(val string: String) : SurveySectionInput()
    data class NumberInput(val number: Int) : SurveySectionInput()
    data class NumberArrayInput(val numberArray: List<Int>) : SurveySectionInput()
    data class StringArrayInput(val stringArray: List<String>) : SurveySectionInput()
}
