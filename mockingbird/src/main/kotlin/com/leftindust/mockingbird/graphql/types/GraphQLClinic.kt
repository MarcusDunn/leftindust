package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.entity.Clinic
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("Clinic")
data class GraphQLClinic(
    val cid: ID,
    val address: GraphQLAddress,
    private val authContext: GraphQLAuthContext
) {

    @GraphQLName("ClinicId")
    data class ID(val id: UUID)

    constructor(clinic: Clinic, authContext: GraphQLAuthContext) : this(
        cid = ID(clinic.id!!),
        address = GraphQLAddress(clinic.address),
        authContext = authContext,
    )

    suspend fun doctors(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao
    ): List<GraphQLDoctor> = withContext(Dispatchers.IO) {
        doctorDao.getByClinic(cid, authContext.mediqAuthToken)
    }.map { GraphQLDoctor(it, authContext) }
}
