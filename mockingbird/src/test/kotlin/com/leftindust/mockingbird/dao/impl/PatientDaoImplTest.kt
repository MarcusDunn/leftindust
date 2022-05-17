package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.survey.PatientSurveyEntity
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.survey.HibernateAssignedFormRepository
import com.leftindust.mockingbird.doctor.HibernateDoctorPatientRepository
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.event.HibernateEventRepository
import com.leftindust.mockingbird.survey.HibernateSurveyRepository
import com.leftindust.mockingbird.visit.HibernateVisitRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.visit.VisitDto
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.patient.*
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PatientDaoImplTest {
    private val authorizer = mockk<Authorizer>()
    private val patientRepository = mockk<HibernatePatientRepository>()
    private val doctorRepository = mockk<DoctorRepository>()
    private val doctorPatientRepository = mockk<HibernateDoctorPatientRepository>()
    private val visitRepository = mockk<HibernateVisitRepository>()
    private val entityManager = mockk<EntityManager>()
    private val eventRepository = mockk<HibernateEventRepository>()
    private val formRepository = mockk<HibernateSurveyRepository>()
    private val assignedFormRepository = mockk<HibernateAssignedFormRepository>()


    @Test
    fun getByPID() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )
        val actual = patientDaoImpl.getByPatientId(PatientDto.PatientDtoId(patientID))

        assertEquals(mockkPatient, actual)

    }

    @Test
    fun addNewPatient() {
        val createPatientDto = CreatePatientDto(
            nameInfo = CreateNameInfoDto(
                firstName = "hello",
                lastName = "world",
            ),
            dateOfBirth = GraphQLDateInput(year = 2020, day = 10, month = GraphQLMonth.Feb),
            sex = Sex.Male
        )
        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.save(any()) } returns mockkPatient

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.addNewPatient(createPatientDto, mockk())

        assertEquals(mockkPatient, actual)
    }


    @Test
    fun removePatientByPID() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient
        every { patientRepository.delete(any()) } returns Unit


        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.removeByPID(PatientDto.PatientDtoId(patientID), mockk())

        assertEquals(mockkPatient, actual)
    }

    @Test
    fun getByDoctor() {
        val doctorID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { doctorRepository.getById(doctorID) } returns mockk {
            every { id } returns doctorID
        }
        every { doctorPatientRepository.getAllByDoctorId(doctorID) } returns setOf(mockk {
            every { patient } returns mockkPatient
        })

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.getByDoctor(DoctorDto.DoctorDtoId(doctorID), mockk())

        assertEquals(listOf(mockkPatient), actual)
    }

    @Test
    fun getByVisit() {
        val visitID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { visitRepository.getByIdWithEventPatients(visitID) } returns mockk {
            every { event } returns mockk {
                every { patients } returns mutableSetOf(mockkPatient)
            }
        }

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.getVisitPatients(VisitDto.VisitDtoId(visitID), mockk())

        assertEquals(listOf(mockkPatient), actual.toList())
    }

    @Test
    fun addDoctorToPatient() {
        val mockkPatient = mockk<Patient>()
        val mockkDoctor = mockk<Doctor>()

        val patientID = UUID.randomUUID()
        val doctorID = UUID.randomUUID()

        every { mockkPatient.addDoctor(mockkDoctor) } returns mockkPatient

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient
        every { doctorRepository.getById(doctorID) } returns mockkDoctor

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.addDoctorToPatient(
            PatientDto.PatientDtoId(patientID),
            DoctorDto.DoctorDtoId(doctorID),
            mockk()
        )


        assertEquals(mockkPatient, actual)
    }

    @Test
    fun update() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient> {
            every { setByGqlInput(any(), any()) } returns Unit
        }
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val patientInput = mockk<UpdatePatientDto> {
            every { pid } returns PatientDto.PatientDtoId(patientID)
        }

        val actual = patientDaoImpl.update(patientInput, mockk())

        assertEquals(mockkPatient, actual)
    }

    @Test
    fun getByUser() {
        val expected = EntityStore.patient("PatientDaoImplTest.getByUser")

        every { patientRepository.findByUser_UniqueId("uid") } returns expected

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.getByUser("uid", mockk())

        assertEquals(expected, actual)
    }

    @Test
    fun `test assignForms`() {
        val form = EntityStore.form("PatientDaoImplTest.test assignForms")
        val patient = EntityStore.patient("PatientDaoImplTest.test assignForms")

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        every { formRepository.getById(any()) } returns form
        every { patientRepository.findAllById(any()) } returns listOf(patient)

        val patientDaoImpl = PatientServiceImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        every { assignedFormRepository.save(any()) } returns PatientSurveyEntity(form, patient).apply { id = makeUUID("yeet") }

        patientDaoImpl.assignForms(
            listOf(PatientDto.PatientDtoId(makeUUID("qwsdq"))),
            SurveyDto.SurveyDtoId(makeUUID("d3f")),
            mockk()
        )

        assertEquals(
            patient.patientFormEntities.onEach { it.id = makeUUID("yeet") }.toList(),
            listOf(PatientSurveyEntity(form, patient).apply { id = makeUUID("yeet") })
        )
    }
}