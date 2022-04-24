package com.leftindust.mockingbird.doctor

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import graphql.schema.DataFetchingEnvironment
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
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLDoctor> = when {
        dids != null && pid == null && range == null && example == null -> dids
            .map { doctorDao.getByDoctor(it, dataFetchingEnvironment.authToken) }
        pid != null && dids == null && range == null && example == null -> withContext(Dispatchers.IO) {
            doctorDao.getPatientDoctors(pid, dataFetchingEnvironment.authToken)
        }
        range != null && dids == null && pid == null && example == null -> {
            withContext(Dispatchers.IO) {
                doctorDao.getMany(range, dataFetchingEnvironment.authToken)
            }
        }
        example != null && pid == null && range == null && dids == null -> {
            withContext(Dispatchers.IO) {
                doctorDao.searchByExample(example, dataFetchingEnvironment.authToken)
            }
        }
        else -> throw IllegalArgumentException("invalid argument combination to doctors")
    }.map(::GraphQLDoctor)
}
