package com.leftindust.mockingbird.doctor

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class DoctorQuery(
    private val doctorDao: DoctorDao,
) : Query {
    suspend fun doctorsById(
        dids: List<GraphQLDoctor.ID>,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = dids.map { doctorDao.getByDoctor(it, dataFetchingEnvironment.authToken) }.map(::GraphQLDoctor)

    suspend fun doctorsByPatientId(
        pid: GraphQLPatient.ID,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ) = doctorDao.getPatientDoctors(pid, dataFetchingEnvironment.authToken).map(::GraphQLDoctor)

    suspend fun doctorsByRange(
        range: GraphQLRangeInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = doctorDao.getMany(range, dataFetchingEnvironment.authToken).map(::GraphQLDoctor)

    suspend fun doctorsByExample(
        example: GraphQLDoctorExample,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = doctorDao.searchByExample(example, dataFetchingEnvironment.authToken).map(::GraphQLDoctor)

    @GraphQLDescription("only pass one variable")
    @Deprecated("removed due to lack of query verification", replaceWith = ReplaceWith("doctorsByExample"))
    suspend fun doctors(
        dids: List<GraphQLDoctor.ID>? = null,
        pid: GraphQLPatient.ID? = null,
        range: GraphQLRangeInput? = null,
        example: GraphQLDoctorExample? = null,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLDoctor> = when {
        dids != null && pid == null && range == null && example == null -> doctorsById(dids, dataFetchingEnvironment)
        pid != null && dids == null && range == null && example == null -> doctorsByPatientId(pid, dataFetchingEnvironment)
        range != null && dids == null && pid == null && example == null -> doctorsByRange(range, dataFetchingEnvironment)
        example != null && pid == null && range == null && dids == null -> doctorsByExample(example, dataFetchingEnvironment)
        else -> throw IllegalArgumentException("invalid argument combination to doctors")
    }
}
