package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.ContactDao
import com.leftindust.mockingbird.dao.entity.EmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ContactQueryTest {
    private val contactDao = mockk<ContactDao>()

    @Test
    fun getContactsByPatient() {
        val patientId = UUID.randomUUID()

        val mockkContact = mockk<EmergencyContact>(relaxed = true)

        val graphQLEmergencyContact = GraphQLEmergencyContact(mockkContact)

        every { contactDao.getPatientContacts(GraphQLPatient.ID(patientId), any()) } returns listOf(mockkContact)

        val contactQuery = ContactQuery(contactDao)

        val result = runBlocking {
            contactQuery.getContactsByPatient(
                pid = GraphQLPatient.ID(patientId),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(graphQLEmergencyContact), result)
    }
}