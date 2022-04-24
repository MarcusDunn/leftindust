package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.ContactDao
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLPerson
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ContactQuery(
    private val contactDao: ContactDao
) : Query {
    suspend fun getContactsByPatient(
        pid: GraphQLPatient.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPerson> = withContext(Dispatchers.IO) {
        contactDao.getPatientContacts(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLEmergencyContact)
}