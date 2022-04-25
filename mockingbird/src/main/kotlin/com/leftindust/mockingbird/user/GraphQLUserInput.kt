package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.person.GraphQLNameInfoInput

@GraphQLName("UserInput")
data class GraphQLUserInput(
    val uid: String,
    val nameInfo: GraphQLNameInfoInput,
    val group: GraphQLUserGroup.ID? = null,
    val doctor: GraphQLDoctor.ID? = null,
)