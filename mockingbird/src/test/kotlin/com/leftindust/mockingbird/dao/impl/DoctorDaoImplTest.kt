package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.entity.DoctorPatient
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.dao.impl.repository.HibernateClinicRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorPatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorInput
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verifyAll
import java.util.UUID
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DoctorDaoImplTest {
    private val authorizer = mockk<Authorizer>()
    private val doctorRepository = mockk<HibernateDoctorRepository>()
    private val doctorPatientRepository = mockk<HibernateDoctorPatientRepository>()
    private val patientRepository = mockk<HibernatePatientRepository>()
    private val eventRepository = mockk<HibernateEventRepository>()
    private val clinicRepository = mockk<HibernateClinicRepository>()
    private val entityManager = mockk<EntityManager>()


    @Test
    fun getByPatient() {
        val mockkDoctor = mockk<Doctor>()
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient> {
            every { id } returns patientID
        }
        every { patientRepository.getById(any()) } returns mockkPatient
        val doctorPatient = mockk<DoctorPatient> {
            every { doctor } returns mockkDoctor
        }
        every { doctorPatientRepository.getAllByPatientId(patientID) } returns setOf(doctorPatient)


        val doctorDaoImpl = DoctorDaoImpl(
            authorizer, doctorRepository, doctorPatientRepository,
            patientRepository, eventRepository, clinicRepository, entityManager
        )

        val actual = doctorDaoImpl.getPatientDoctors(GraphQLPatient.ID(patientID), mockk())

        assertEquals(listOf(mockkDoctor), actual)

        verifyAll {
            authorizer.getAuthorization(any(), any())
            patientRepository.getById(patientID)
            mockkPatient.id
            doctorPatientRepository.getAllByPatientId(patientID)
            doctorPatient.doctor
        }
    }

    @Test
    fun getByEvent() {
        val mockkDoctor = mockk<Doctor>()
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        val mockkEvent = mockk<Event> {
            every { doctors } returns mutableSetOf(mockkDoctor)
        }
        val eventID = UUID.randomUUID()

        every { eventRepository.getById(eventID) } returns mockkEvent

        val doctorDaoImpl =
            DoctorDaoImpl(
                authorizer,
                doctorRepository,
                doctorPatientRepository,
                patientRepository,
                eventRepository,
                clinicRepository, entityManager
            )
        val actual = doctorDaoImpl.getByEvent(GraphQLEvent.ID(eventID), mockk())

        assertEquals(setOf(mockkDoctor), actual)

        verifyAll {
            authorizer.getAuthorization(any(), any())
            mockkEvent.doctors
            eventRepository.getById(eventID)
        }
    }

    @Test
    fun getByDoctor() {
        val mockkDoctor = mockk<Doctor>()
        val doctorID = UUID.randomUUID()

        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { doctorRepository.getById(doctorID) } returns mockkDoctor

        val doctorDaoImpl =
            DoctorDaoImpl(
                authorizer,
                doctorRepository,
                doctorPatientRepository,
                patientRepository,
                eventRepository,
                clinicRepository, entityManager
            )
        val actual = doctorDaoImpl.getByDoctor(GraphQLDoctor.ID(doctorID), mockk())

        assertEquals(mockkDoctor, actual)

        verifyAll {
            authorizer.getAuthorization(any(), any())
            doctorRepository.getById(doctorID)
        }
    }

    @Test
    fun addDoctor() {
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val doctor = mockk<Doctor>()
        every { doctorRepository.save(any()) } returns doctor

        val graphQLDoctorInput = mockk<GraphQLDoctorInput>(relaxed = true)

        every { patientRepository.findAllById(emptyList()) } returns emptyList<Patient>()

        val doctorDaoImpl =
            DoctorDaoImpl(
                authorizer,
                doctorRepository,
                doctorPatientRepository,
                patientRepository,
                eventRepository,
                clinicRepository,
                entityManager
            )

        val result = doctorDaoImpl.addDoctor(graphQLDoctorInput, mockk())

        assertEquals(doctor, result)

        verifyAll {
            authorizer.getAuthorization(any(), any())
            doctorRepository.save(any())
        }
    }

    @Test
    fun editDoctor() {
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val doctorID = UUID.randomUUID()

        val graphQLDoctorInput = mockk<GraphQLDoctorEditInput> {
            every { did } returns GraphQLDoctor.ID(doctorID)
        }

        val mockkDoctor = mockk<Doctor> {
            every { setByGqlInput(graphQLDoctorInput, any()) } just runs
        }


        every { doctorRepository.getById(doctorID) } returns mockkDoctor

        val doctorDaoImpl =
            DoctorDaoImpl(
                authorizer,
                doctorRepository,
                doctorPatientRepository,
                patientRepository,
                eventRepository,
                clinicRepository, entityManager
            )

        val result = doctorDaoImpl.editDoctor(graphQLDoctorInput, mockk())

        verifyAll {
            mockkDoctor.setByGqlInput(graphQLDoctorInput, any())
        }

        assertEquals(mockkDoctor, result)
    }

    @Test
    internal fun `edit doctor with insufficient perms`() {
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Denied

        val doctorID = UUID.randomUUID()


        val graphQLDoctorInput = mockk<GraphQLDoctorEditInput> {
            every { did } returns GraphQLDoctor.ID(doctorID)
        }

        val mockkDoctor = mockk<Doctor> {
            every { setByGqlInput(graphQLDoctorInput, any()) } just runs
        }

        every { doctorRepository.getById(doctorID) } returns mockkDoctor

        val doctorDaoImpl = DoctorDaoImpl(
            authorizer,
            doctorRepository,
            doctorPatientRepository,
            patientRepository,
            eventRepository,
            clinicRepository,
            entityManager
        )

        assertThrows<NotAuthorizedException> {
            doctorDaoImpl.editDoctor(graphQLDoctorInput, mockk())
        }
    }

    @Test
    fun getByClinic() {
        val clinicId = UUID.randomUUID()

        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val mockkDoctor = mockk<Doctor>()

        every { clinicRepository.getById(clinicId) } returns mockk {
            every { doctors } returns mutableSetOf(mockkDoctor)
        }

        val doctorDaoImpl = DoctorDaoImpl(
            authorizer,
            doctorRepository,
            doctorPatientRepository,
            patientRepository,
            eventRepository,
            clinicRepository, entityManager
        )

        val result = doctorDaoImpl.getByClinic(GraphQLClinic.ID(clinicId), mockk())
        
        assertEquals(setOf(mockkDoctor), result)
    }
}