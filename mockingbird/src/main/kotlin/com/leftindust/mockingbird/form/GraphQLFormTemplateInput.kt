package com.leftindust.mockingbird.form

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("FormTemplateInput")
data class GraphQLFormTemplateInput(
    val name: String,
    val sections: List<GraphQLFormSectionInput>,
)

