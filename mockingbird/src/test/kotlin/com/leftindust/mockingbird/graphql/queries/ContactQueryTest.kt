package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.contact.ContactQuery
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ContactQueryTest {
    private val readContactService = mockk<ReadContactService>()

    @Test
    fun getContactsByPatient() {
        val patientId = UUID.randomUUID()

        val mockkContact = mockk<Contact>(relaxed = true)

        val contactDto = ContactDto(mockkContact)

        every { readContactService.getByPatientId(PatientDto.PatientDtoId(patientId), any()) } returns listOf(mockkContact)

        val contactQuery = ContactQuery(readContactService)

        val result = runBlocking {
            contactQuery.getContactsByPatient(
                patientId = PatientDto.PatientDtoId(patientId),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(contactDto), result)
    }
}