package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.DataType

@GraphQLName("FormTemplateInput")
data class GraphQLFormTemplateInput(
    val name: String,
    val sections: List<GraphQLFormSectionInput>,
)

@GraphQLName("FormSectionInput")
data class GraphQLFormSectionInput(
    val name: String,
    val number: Int,
    val description: String? = null,
    val fields: List<GraphQlFormFieldInput>,
)

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

