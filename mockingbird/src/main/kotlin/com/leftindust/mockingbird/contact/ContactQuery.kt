package com.leftindust.mockingbird.contact

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.person.GraphQLPerson
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