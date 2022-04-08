package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor

@GraphQLName("ClinicInput")
data class GraphQLClinicInput(
    val name: String,
    val address: GraphQLAddressInput,
    @GraphQLDescription("defaults to empty list")
    val doctors: List<GraphQLDoctor.ID>? = null
)

@GraphQLName("ClinicEditInput")
data class GraphQLClinicEditInput(
    val cid: GraphQLClinic.ID,
    val name: String? = null,
    val address: GraphQLAddressEditInput? = null,
    @GraphQLDescription("passing null will not update, to clear: pass an empty list")
    val doctors: List<GraphQLDoctor.ID>? = null
)