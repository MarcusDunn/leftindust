package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput

class GraphQLVisitEditInput(
    val vid: VisitDto.VisitDtoId,
    val title: Deletable<String>,
    val description: Deletable<String>,
    val foundationIcdCodes: Updatable<List<GraphQLFoundationIcdCodeInput>>,
)
