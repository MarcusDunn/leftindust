package com.leftindust.mockingbird.dao.email.impl

import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.entity.Email
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.util.makeUUID
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import com.leftindust.mockingbird.util.unit.StrictAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ReadEmailDaoImplTest {
    @Nested
    inner class Authenticated : LenientAuthorizerUnitTest() {

        @Test
        fun `check getDoctorEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
                doctorRepository = mockk {
                    every { getById(any()) } returns mockk {
                        every { email } returns mutableSetOf()
                    }
                },
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            val result = readEmailDaoImpl.getDoctorEmails(GraphQLDoctor.ID(makeUUID("getDoctorEmails")), mockk())
            assertEquals(emptyList<Email>(), result)
        }

        @Test
        fun `check getEmergencyContactEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk {
                    every { getById(any()) } returns mockk {
                        every { email } returns mutableSetOf()
                    }
                },
                patientRepository = mockk(),
                authorizer = authorizer
            )
            val result = readEmailDaoImpl.getEmergencyContactEmails(
                GraphQLEmergencyContact.ID(makeUUID("getEmergencyContactEmails")),
                mockk()
            )
            assertEquals(emptyList<Email>(), result)
        }

        @Test
        fun `check getPatientEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
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
                readEmailDaoImpl.getPatientEmails(GraphQLPatient.ID(makeUUID("getEmergencyContactEmails")), mockk())
            assertEquals(emptyList<Email>(), result)
        }
    }

    @Nested
    inner class Unauthenticated : StrictAuthorizerUnitTest() {
        @Test
        fun `check getDoctorEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getDoctorEmails(
                    GraphQLDoctor.ID(makeUUID("getDoctorEmails")),
                    mockk()
                )
            }
        }

        @Test
        fun `check getEmergencyContactEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getEmergencyContactEmails(
                    GraphQLEmergencyContact.ID(makeUUID("getEmergencyContactEmails")),
                    mockk()
                )
            }
        }

        @Test
        fun `check getPatientEmails`() {
            val readEmailDaoImpl = ReadEmailDaoImpl(
                doctorRepository = mockk(),
                emergencyContactRepository = mockk(),
                patientRepository = mockk(),
                authorizer = authorizer
            )
            assertThrows<NotAuthorizedException> {
                readEmailDaoImpl.getPatientEmails(
                    GraphQLPatient.ID(makeUUID("getEmergencyContactEmails")),
                    mockk()
                )
            }
        }
    }
}