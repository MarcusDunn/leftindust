package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.dao.entity.AssignedForm
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.dao.entity.enums.Sex
import com.leftindust.mockingbird.dao.impl.repository.HibernateAssignedFormRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorPatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateFormRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateVisitRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.GraphQLMonth
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLDateInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLNameInfoInput
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
    private val doctorRepository = mockk<HibernateDoctorRepository>()
    private val doctorPatientRepository = mockk<HibernateDoctorPatientRepository>()
    private val visitRepository = mockk<HibernateVisitRepository>()
    private val entityManager = mockk<EntityManager>()
    private val eventRepository = mockk<HibernateEventRepository>()
    private val formRepository = mockk<HibernateFormRepository>()
    private val assignedFormRepository = mockk<HibernateAssignedFormRepository>()


    @Test
    fun getByPID() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )
        val actual = patientDaoImpl.getByPID(GraphQLPatient.ID(patientID), mockk())

        assertEquals(mockkPatient, actual)

    }

    @Test
    fun addNewPatient() {
        val graphQLPatientInput = GraphQLPatientInput(
            nameInfo = GraphQLNameInfoInput(
                firstName = "hello",
                lastName = "world",
            ),
            dateOfBirth = GraphQLDateInput(year = 2020, day = 10, month = GraphQLMonth.Feb),
            sex = Sex.Male
        )
        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.save(any()) } returns mockkPatient

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.addNewPatient(graphQLPatientInput, mockk())

        assertEquals(mockkPatient, actual)
    }


    @Test
    fun removePatientByPID() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { patientRepository.getById(patientID) } returns mockkPatient
        every { patientRepository.delete(any()) } returns Unit


        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.removeByPID(GraphQLPatient.ID(patientID), mockk())

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

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.getByDoctor(GraphQLDoctor.ID(doctorID), mockk())

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

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.getVisitPatients(GraphQLVisit.ID(visitID), mockk())

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

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val actual = patientDaoImpl.addDoctorToPatient(
            GraphQLPatient.ID(patientID),
            GraphQLDoctor.ID(doctorID),
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

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        val patientInput = mockk<GraphQLPatientEditInput> {
            every { pid } returns GraphQLPatient.ID(patientID)
        }

        val actual = patientDaoImpl.update(patientInput, mockk())

        assertEquals(mockkPatient, actual)
    }

    @Test
    fun getByUser() {
        val expected = EntityStore.patient("PatientDaoImplTest.getByUser")

        every { patientRepository.findByUser_UniqueId("uid") } returns expected

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val patientDaoImpl = PatientDaoImpl(
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

        val patientDaoImpl = PatientDaoImpl(
            authorizer, patientRepository, doctorRepository,
            doctorPatientRepository, eventRepository, visitRepository, entityManager, formRepository,
            assignedFormRepository
        )

        every { assignedFormRepository.save(any()) } returns AssignedForm(form, patient).apply { id = makeUUID("yeet") }

        patientDaoImpl.assignForms(
            listOf(GraphQLPatient.ID(makeUUID("qwsdq"))),
            GraphQLFormTemplate.ID(makeUUID("d3f")),
            mockk()
        )

        assertEquals(
            patient.assignedForms.onEach { it.id = makeUUID("yeet") }.toList(),
            listOf(AssignedForm(form, patient).apply { id = makeUUID("yeet") })
        )
    }
}