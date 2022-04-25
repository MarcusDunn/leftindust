package com.leftindust.mockingbird.clinic

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddressEditInput
import com.leftindust.mockingbird.address.GraphQLAddressInput
import com.leftindust.mockingbird.clinic.GraphQLClinic
import com.leftindust.mockingbird.doctor.GraphQLDoctor

@GraphQLName("ClinicInput")
data class GraphQLClinicInput(
    val name: String,
    val address: GraphQLAddressInput,
    @GraphQLDescription("defaults to empty list")
    val doctors: List<GraphQLDoctor.ID>? = null
)

