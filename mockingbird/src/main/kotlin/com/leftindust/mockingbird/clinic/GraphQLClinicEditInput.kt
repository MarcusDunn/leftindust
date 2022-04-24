package com.leftindust.mockingbird.clinic

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddressEditInput
import com.leftindust.mockingbird.doctor.GraphQLDoctor

@GraphQLName("ClinicEditInput")
data class GraphQLClinicEditInput(
    val cid: GraphQLClinic.ID,
    val name: String? = null,
    val address: GraphQLAddressEditInput? = null,
    @GraphQLDescription("passing null will not update, to clear: pass an empty list")
    val doctors: List<GraphQLDoctor.ID>? = null
)