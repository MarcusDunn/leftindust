package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLDoctorExample
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DoctorQuery(
    private val doctorDao: DoctorDao,
) : Query {
    @GraphQLDescription("only pass one variable")
    suspend fun doctors(
        dids: List<GraphQLDoctor.ID>? = null,
        pid: GraphQLPatient.ID? = null,
        range: GraphQLRangeInput? = null,
        example: GraphQLDoctorExample? = null,
        authContext: GraphQLAuthContext
    ): List<GraphQLDoctor> = when {
        dids != null && pid == null && range == null && example == null -> dids
            .map { doctorDao.getByDoctor(it, authContext.mediqAuthToken) }
        pid != null && dids == null && range == null && example == null -> withContext(Dispatchers.IO) {
            doctorDao.getPatientDoctors(pid, authContext.mediqAuthToken)
        }
        range != null && dids == null && pid == null && example == null -> {
            withContext(Dispatchers.IO) {
                doctorDao.getMany(range, authContext.mediqAuthToken)
            }
        }
        example != null && pid == null && range == null && dids == null -> {
            withContext(Dispatchers.IO) {
                doctorDao.searchByExample(example, authContext.mediqAuthToken)
            }
        }
        else -> throw IllegalArgumentException("invalid argument combination to doctors")
    }.map { GraphQLDoctor(it, authContext) }
}
