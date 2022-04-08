package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.ContactDao
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ContactQuery(
    private val contactDao: ContactDao
) : Query {
    @Throws(GraphQLKotlinException::class)
    suspend fun getContactsByPatient(
        pid: GraphQLPatient.ID, authContext: GraphQLAuthContext
    ): List<GraphQLPerson> = withContext(Dispatchers.IO) {
        contactDao.getPatientContacts(pid, authContext.mediqAuthToken)
    }.map { GraphQLEmergencyContact(it, authContext) }
}