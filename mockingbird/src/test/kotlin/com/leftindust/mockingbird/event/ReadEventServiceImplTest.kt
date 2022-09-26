package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.EventMother
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.VisitMother
import com.leftindust.mockingbird.visit.ReadVisitService
import com.leftindust.mockingbird.visit.VisitDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
@DataJpaTest
internal class ReadEventServiceImplUnitTest {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockK
    private lateinit var eventRepository: HibernateEventRepository

    @MockK
    private lateinit var doctorRepository: DoctorRepository

    @MockK
    private lateinit var patientRepository: PatientRepository

    @MockK
    private lateinit var readVisitService: ReadVisitService

    @Test
    internal fun `check getByPatientId returns a patient's event when patient exists`() = runTest {
        coEvery { patientRepository.findByIdOrNull(PatientMother.Dan.id) } returns PatientMother.Dan.entityDetached
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val events = readEventServiceImpl.getByPatientId(PatientMother.Dan.graphqlId)

        assertThat(events, containsInAnyOrder(PatientMother.Dan.events.map { equalTo(it.event) }))
    }

    @Test
    internal fun `check getByPatientId returns null when no matching patient exists corresponding to any events`() =
        runTest {
            val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            coEvery { patientRepository.findByIdOrNull(match { it == someNonExistentUUid }) } returns null
            val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
            val events = readEventServiceImpl.getByPatientId(PatientDto.PatientDtoId(someNonExistentUUid))

            assertThat(events, nullValue())
        }

    @Test
    internal fun `check getByDoctorId returns a doctor's event when doctor exists`() = runTest {
        coEvery { doctorRepository.findByIdOrNull(DoctorMother.Jenny.id) } returns DoctorMother.Jenny.entityPersisted
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val events = readEventServiceImpl.getByDoctorId(DoctorMother.Jenny.graphqlId)

        assertThat(events, containsInAnyOrder(DoctorMother.Jenny.events.map { equalTo(it.event) }))
    }

    @Test
    internal fun `check getByDoctorId returns null when no matching doctor exists corresponding to any events`() =
        runTest {
            val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            coEvery { doctorRepository.findByIdOrNull(match { it == someNonExistentUUid }) } returns null
            val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
            val events = readEventServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(someNonExistentUUid))

            assertThat(events, nullValue())
        }

    @Test
    internal fun `check getByVisitId returns a visitId's event when visit exists`() = runTest {
        val jennyDoctorVisit = VisitMother.jennyVisitPersisted
        coEvery { readVisitService.getByVisitId(VisitDto.VisitDtoId(jennyDoctorVisit.id!!)) } returns jennyDoctorVisit
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val event = readEventServiceImpl.getByVisitId(VisitDto.VisitDtoId(jennyDoctorVisit.id!!))

        assertThat(event, equalTo(jennyDoctorVisit.event))
    }

    @Test
    internal fun `check getByVisitId returns null when no matching visit corresponding to an event exists`() = runTest {
        val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        coEvery { readVisitService.getByVisitId(VisitDto.VisitDtoId(someNonExistentUUid)) } returns null
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val events = readEventServiceImpl.getByVisitId(VisitDto.VisitDtoId(someNonExistentUUid))

        assertThat(events, nullValue())
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
@DataJpaTest
internal class ReadEventServiceImplDataTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val eventRepository: HibernateEventRepository,
) {
    @MockkBean
    private lateinit var securityWebFilterChain: SecurityWebFilterChain

    @MockK
    private lateinit var doctorRepository: DoctorRepository

    @MockK
    private lateinit var patientRepository: PatientRepository

    @MockkBean
    private lateinit var readVisitService: ReadVisitService

    @Test
    internal fun `check returns an event when queried with an id from the database with the matching Id`() = runTest {
        val jennyDoctorAppointmentId =
            testEntityManager.persistAndGetId(EventMother.jennyAppointmentUnpersisted, UUID::class.java)
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val returnedEvent = readEventServiceImpl.getByEventId(EventDto.EventDtoId(jennyDoctorAppointmentId!!))

        assertThat(returnedEvent, Matchers.notNullValue())
        assertThat(returnedEvent!!.id, equalTo(jennyDoctorAppointmentId))
    }

    @Test
    internal fun `check returns null when the database has no matching Id corresponding to an event`() = runTest {
        val someNonExistentUuid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        val readEventServiceImpl = ReadEventServiceImpl(eventRepository, patientRepository, readVisitService, doctorRepository)
        val returnedEvent = readEventServiceImpl.getByEventId(EventDto.EventDtoId(someNonExistentUuid))

        assertThat(returnedEvent, nullValue())
    }
}
