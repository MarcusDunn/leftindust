package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.entity.Clinic
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLName("Clinic")
data class GraphQLClinic(
    val cid: ID,
    val address: GraphQLAddress,
) {

    @GraphQLName("ClinicId")
    data class ID(val id: UUID)

    constructor(clinic: Clinic) : this(
        cid = ID(clinic.id!!),
        address = GraphQLAddress(clinic.address),
    )

    suspend fun doctors(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLDoctor> = withContext(Dispatchers.IO) {
        doctorDao.getByClinic(cid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLDoctor)
}
