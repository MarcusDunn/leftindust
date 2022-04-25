package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput

@GraphQLName("VisitInput")
data class GraphQLVisitInput(
    val eid: GraphQLEvent.ID,
    val title: String? = null,
    val description: String? = null,
    val foundationIcdCodes: List<GraphQLFoundationIcdCodeInput>,
)