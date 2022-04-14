package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.clinic.CreateClinicDao
import com.leftindust.mockingbird.dao.clinic.UpdateClinicDao
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicInput
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