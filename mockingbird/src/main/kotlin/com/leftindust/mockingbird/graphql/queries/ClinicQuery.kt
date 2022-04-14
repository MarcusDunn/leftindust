package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.clinic.ReadClinicDao
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
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