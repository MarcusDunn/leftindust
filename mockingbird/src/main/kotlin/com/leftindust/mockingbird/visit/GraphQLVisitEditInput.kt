package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput

class GraphQLVisitEditInput(
    val vid: VisitDto.VisitDtoId,
    val eid: EventDto.EventDtoId?,
    val title: Updatable<String?>,
    val description: Updatable<String?>,
    val foundationIcdCodes: List<GraphQLFoundationIcdCodeInput>?,
)
