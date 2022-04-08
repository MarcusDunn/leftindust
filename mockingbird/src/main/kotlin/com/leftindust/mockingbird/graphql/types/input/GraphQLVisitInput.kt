package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.icd.GraphQLFoundationIcdCodeInput

@GraphQLName("VisitInput")
data class GraphQLVisitInput(
    val eid: GraphQLEvent.ID,
    val title: String? = null,
    val description: String? = null,
    val foundationIcdCodes: List<GraphQLFoundationIcdCodeInput>,
)