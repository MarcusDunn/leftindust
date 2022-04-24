package com.leftindust.mockingbird.clinic

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ClinicMutation(
    private val createClinicDao: CreateClinicDao,
    private val updateClinicDao: UpdateClinicDao
) : Mutation {
    suspend fun addClinic(
        clinic: GraphQLClinicInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLClinic = withContext(Dispatchers.IO) {
        createClinicDao.addClinic(clinic, dataFetchingEnvironment.authToken)
    }.let(::GraphQLClinic)

    suspend fun editClinic(
        clinic: GraphQLClinicEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLClinic = withContext(Dispatchers.IO) {
        updateClinicDao.editClinic(clinic, dataFetchingEnvironment.authToken)
    }.let(::GraphQLClinic)
}