package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ReadEventServiceImplUnitTest {
    @MockK
    private lateinit var eventRepository: HibernateEventRepository

    @MockK
    private lateinit var readPatientService: ReadPatientService

    @MockK
    private lateinit var readDoctorService: ReadDoctorService

    @MockK
    private lateinit var readVisitService: ReadVisitService

    @Test
    internal fun `check getByPatientId returns a patient's event when patient exists`() = runTest {
        val jennyThePatient = PatientMother.jennyThePatientPersisted
        coEvery { readPatientService.getByPatientId(match { it.value == jennyThePatient.id }) } returns jennyThePatient
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val events = readEventServiceImpl.getByPatientId(PatientDto.PatientDtoId(jennyThePatient.id!!))

        assertThat(events, equalTo(jennyThePatient.events.map { it.event }))
    }

    @Test
    internal fun `check getByPatientId returns null when no matching patient exists corresponding to any events`() =
        runTest {
            val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            coEvery { readPatientService.getByPatientId(match { it.value == someNonExistentUUid }) } returns null
            val readEventServiceImpl =
                ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
            val events = readEventServiceImpl.getByPatientId(PatientDto.PatientDtoId(someNonExistentUUid))

            assertThat(events, nullValue())
        }

    @Test
    internal fun `check getByDoctorId returns a doctor's event when doctor exists`() = runTest {
        val jennyTheDoctor = DoctorMother.jennyTheDoctorPersisted
        coEvery { readDoctorService.getByDoctorId(match { it.value == jennyTheDoctor.id }) } returns jennyTheDoctor
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val events = readEventServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(jennyTheDoctor.id!!))

        assertThat(events, equalTo(jennyTheDoctor.events.map { it.event }))
    }

    @Test
    internal fun `check getByDoctorId returns null when no matching doctor exists corresponding to any events`() =
        runTest {
            val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            coEvery { readDoctorService.getByDoctorId(match { it.value == someNonExistentUUid }) } returns null
            val readEventServiceImpl =
                ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
            val events = readEventServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(someNonExistentUUid))

            assertThat(events, nullValue())
        }

    @Test
    internal fun `check getByVisitId returns a visitId's event when visit exists`() = runTest {
        val jennyDoctorVisit = VisitMother.jennyVisitPersisted
        coEvery { readVisitService.getByVisitId(VisitDto.VisitDtoId(jennyDoctorVisit.id!!)) } returns jennyDoctorVisit
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val events = readEventServiceImpl.getByVisitId(VisitDto.VisitDtoId(jennyDoctorVisit.id!!))

        assertThat(events, equalTo(jennyDoctorVisit.event))
    }

    @Test
    internal fun `check getByVisitId returns null when no matching visit corresponding to an event exists`() = runTest {
        val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        coEvery { readVisitService.getByVisitId(VisitDto.VisitDtoId(someNonExistentUUid)) } returns null
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val events = readEventServiceImpl.getByVisitId(VisitDto.VisitDtoId(someNonExistentUUid))

        assertThat(events, nullValue())
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class ReadEventServiceImplDataTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val eventRepository: HibernateEventRepository,
) {
    @MockkBean
    private lateinit var securityWebFilterChain: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @MockkBean
    private lateinit var readVisitService: ReadVisitService

    @Test
    internal fun `check returns an event when queried with an id from the database with the matching Id`() = runTest {
        val jennyDoctorAppointmentId = testEntityManager.persistAndGetId(EventMother.jennyAppointmentUnpersisted, UUID::class.java)
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val returnedEvent = readEventServiceImpl.getByEventId(EventDto.EventDtoId(jennyDoctorAppointmentId!!))

        assertThat(returnedEvent, Matchers.notNullValue())
        assertThat(returnedEvent!!.id, equalTo(jennyDoctorAppointmentId))
    }

    @Test
    internal fun `check returns null when the database has no matching Id corresponding to an event`() = runTest {
        val someNonExistentUuid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        val readEventServiceImpl =
            ReadEventServiceImpl(eventRepository, readPatientService, readDoctorService, readVisitService)
        val returnedEvent = readEventServiceImpl.getByEventId(EventDto.EventDtoId(someNonExistentUuid))

        assertThat(returnedEvent, nullValue())
    }
}
