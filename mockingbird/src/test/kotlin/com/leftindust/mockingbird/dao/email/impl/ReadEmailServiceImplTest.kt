package com.leftindust.mockingbird.dao.email.impl

import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.ReadEmailServiceImpl
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.util.makeUUID
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import com.leftindust.mockingbird.util.unit.StrictAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ReadEmailServiceImplTest {
    @Nested
    inner class Authenticated : LenientAuthorizerUnitTest() {

        @Test
        fun `check getDoctorEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk {
                    every { getById(any()) } returns mockk {
                        every { email } returns mutableSetOf()
                    }
                },
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            val result = readEmailDaoImpl.getByDoctorId(DoctorDto.DoctorDtoId(makeUUID("getDoctorEmails")), mockk())
            assertEquals(emptyList<Email>(), result)
        }

        @Test
        fun `check getEmergencyContactEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk {
                    every { getById(any()) } returns mockk {
                        every { email } returns mutableSetOf()
                    }
                },
                patientRepository = mockk(),
                authorizer = authorizer
            )
            val result = readEmailDaoImpl.getContactEmails(
                ContactDto.Id(makeUUID("getEmergencyContactEmails")),
                mockk()
            )
            assertEquals(emptyList<Email>(), result)
        }

        @Test
        fun `check getPatientEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk {
                    every { getById(any()) } returns mockk {
                        every { email } returns mutableSetOf()
                    }
                },
                authorizer = authorizer
            )
            val result =
                readEmailDaoImpl.getPatientEmails(PatientDto.PatientDtoId(makeUUID("getEmergencyContactEmails")), mockk())
            assertEquals(emptyList<Email>(), result)
        }
    }

    @Nested
    inner class Unauthenticated : StrictAuthorizerUnitTest() {
        @Test
        fun `check getDoctorEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getByDoctorId(
                    DoctorDto.DoctorDtoId(makeUUID("getDoctorEmails")),
                    mockk()
                )
            }
        }

        @Test
        fun `check getEmergencyContactEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getContactEmails(
                    ContactDto.Id(makeUUID("getEmergencyContactEmails")),
                    mockk()
                )
            }
        }

        @Test
        fun `check getPatientEmails`() {
            val readEmailDaoImpl = ReadEmailServiceImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getPatientEmails(
                    PatientDto.PatientDtoId(makeUUID("getEmergencyContactEmails")),
                    mockk()
                )
            }
        }
    }
}