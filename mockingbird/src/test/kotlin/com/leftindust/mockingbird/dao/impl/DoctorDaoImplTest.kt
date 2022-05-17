package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.clinic.ClinicRepository
import com.leftindust.mockingbird.doctor.HibernateDoctorPatientRepository
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.event.HibernateEventRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.doctor.UpdateDoctorDto
import com.leftindust.mockingbird.doctor.CreateDoctorDto
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
    private val doctorRepository = mockk<DoctorRepository>()
    private val doctorPatientRepository = mockk<HibernateDoctorPatientRepository>()
    private val patientRepository = mockk<HibernatePatientRepository>()
    private val eventRepository = mockk<HibernateEventRepository>()
    private val clinicRepository = mockk<ClinicRepository>()
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

        val actual = doctorDaoImpl.getPatientDoctors(PatientDto.PatientDtoId(patientID), mockk())

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
        val actual = doctorDaoImpl.getByEvent(EventDto.EventDtoId(eventID), mockk())

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
        val actual = doctorDaoImpl.getByDoctor(DoctorDto.DoctorDtoId(doctorID), mockk())

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

        val createDoctorDto = mockk<CreateDoctorDto>(relaxed = true)

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

        val result = doctorDaoImpl.addDoctor(createDoctorDto, mockk())

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

        val doctorDtoInput = mockk<UpdateDoctorDto> {
            every { did } returns DoctorDto.DoctorDtoId(doctorID)
        }

        val mockkDoctor = mockk<Doctor> {
            every { setByGqlInput(doctorDtoInput, any()) } just runs
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

        val result = doctorDaoImpl.editDoctor(doctorDtoInput, mockk())

        verifyAll {
            mockkDoctor.setByGqlInput(doctorDtoInput, any())
        }

        assertEquals(mockkDoctor, result)
    }

    @Test
    internal fun `edit doctor with insufficient perms`() {
        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Denied

        val doctorID = UUID.randomUUID()


        val doctorDtoInput = mockk<UpdateDoctorDto> {
            every { did } returns DoctorDto.DoctorDtoId(doctorID)
        }

        val mockkDoctor = mockk<Doctor> {
            every { setByGqlInput(doctorDtoInput, any()) } just runs
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
            doctorDaoImpl.editDoctor(doctorDtoInput, mockk())
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