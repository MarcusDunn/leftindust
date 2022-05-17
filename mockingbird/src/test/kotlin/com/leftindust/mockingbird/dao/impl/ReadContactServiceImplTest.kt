package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.contact.ReadContactServiceImpl
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.ContactRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.patient.PatientDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class ReadContactServiceImplTest {

    private val authorizer = mockk<Authorizer>()
    private val patientRepository = mockk<ContactRepository>()

    @Test
    fun getByPatient() {
        val contact = mockk<Contact>()
        val patientId = UUID.randomUUID()


        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getAllByPatient_Id(patientId) } returns setOf(contact)

        val contactDaoImpl = ReadContactServiceImpl(authorizer, patientRepository)
        val actual = contactDaoImpl.getPatientContacts(PatientDto.PatientDtoId(patientId), mockk())

        assertIterableEquals(listOf(contact), actual)
    }
}