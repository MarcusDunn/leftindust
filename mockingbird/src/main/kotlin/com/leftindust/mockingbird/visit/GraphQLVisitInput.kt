package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput

data class GraphQLVisitInput(
    val eid: EventDto.EventDtoId,
    val title: String? = null,
    val description: String? = null,
    val foundationIcdCodes: List<GraphQLFoundationIcdCodeInput>,
)