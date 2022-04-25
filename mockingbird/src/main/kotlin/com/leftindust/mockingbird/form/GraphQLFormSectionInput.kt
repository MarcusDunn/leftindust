package com.leftindust.mockingbird.form

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("FormSectionInput")
data class GraphQLFormSectionInput(
    val name: String,
    val number: Int,
    val description: String? = null,
    val fields: List<GraphQlFormFieldInput>,
)