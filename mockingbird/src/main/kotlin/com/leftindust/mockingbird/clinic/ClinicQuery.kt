package com.leftindust.mockingbird.clinic

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ClinicQuery(private val clinicDao: ReadClinicDao) : Query {
    suspend fun clinic(
        cid: GraphQLClinic.ID,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLClinic = withContext(Dispatchers.IO) {
        clinicDao.getByCid(cid, dataFetchingEnvironment.authToken)
    }.let { GraphQLClinic(it) }
}