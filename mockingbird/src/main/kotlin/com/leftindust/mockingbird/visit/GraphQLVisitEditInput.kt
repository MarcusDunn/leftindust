package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput

class GraphQLVisitEditInput(
    @GraphQLDescription("the id of the visit you wish to edit")
    val vid: GraphQLVisit.ID,
    @GraphQLDescription("the new event to attach this to")
    val eid: GraphQLEvent.ID? = null,
    @GraphQLDescription("the new title, explicitly set to null to clear")
    val title: OptionalInput<String?> = OptionalInput.Undefined,
    @GraphQLDescription("the new description, explicitly set to null to clear")
    val description: OptionalInput<String?> = OptionalInput.Undefined,
    @GraphQLDescription("the icd codes for this visit, set to empty array to clear")
    val foundationIcdCodes: List<GraphQLFoundationIcdCodeInput>? = null,
)
