package com.leftindust.mockingbird.form

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.input.GraphQLDateInput

@GraphQLName("FormFieldInput")
data class GraphQlFormFieldInput(
    val title: String,
    val dataType: DataType,
    val number: Int,
    val multiSelectPossibilities: List<String>? = null,
    val intUpperBound: Int? = null,
    val intLowerBound: Int? = null,
    val floatUpperBound: Float? = null,
    val floatLowerBound: Float? = null,
    val dateUpperBound: GraphQLDateInput? = null,
    val dateLowerBound: GraphQLDateInput? = null,
    val textRegex: String? = null,
    val jsonMetaData: String? = null,
) {
    init {
        require(
            when (dataType) {
                DataType.SingleMultiSelect -> !multiSelectPossibilities.isNullOrEmpty()
                DataType.MultiMultiSelect -> !multiSelectPossibilities.isNullOrEmpty()
                else -> true
            }
        ) { "MultiSelect forms must have multiSelectPossibilities" }
    }
}