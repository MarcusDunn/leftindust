package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.ContactDao
import com.leftindust.mockingbird.dao.entity.EmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ContactQueryTest {
    private val contactDao = mockk<ContactDao>()
    private val authContext = mockk<GraphQLAuthContext>()

    @Test
    fun getContactsByPatient() {
        val patientId = UUID.randomUUID()


        val mockkContact = mockk<EmergencyContact>(relaxed = true)

        every { authContext.mediqAuthToken } returns mockk()

        val graphQLEmergencyContact = GraphQLEmergencyContact(mockkContact, authContext)

        every { contactDao.getPatientContacts(GraphQLPatient.ID(patientId), any()) } returns listOf(mockkContact)

        val contactQuery = ContactQuery(contactDao)

        val result = runBlocking { contactQuery.getContactsByPatient(GraphQLPatient.ID(patientId), authContext) }

        assertEquals(listOf(graphQLEmergencyContact), result)
    }
}