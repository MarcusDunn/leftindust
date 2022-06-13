package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.ReadEventService
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.EventMother
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ReadDoctorServiceImplUnitTest {
    @MockK
    private lateinit var doctorRepository: DoctorRepository

    @MockK
    private lateinit var readPatientService: ReadPatientService

    @MockK
    private lateinit var readEventService: ReadEventService

    @Test
    internal fun `check getByEventId returns null if the event does not exist`() = runTest {
        val nonExistentId = EventDto.EventDtoId(UUID.randomUUID())
        // the event does not exist
        coEvery { readEventService.getByEventId(nonExistentId) } returns null

        val readDoctorServiceImpl = ReadDoctorServiceImpl(doctorRepository, readPatientService, readEventService)

        // go get the event with that id
        val doctors = readDoctorServiceImpl.getByEventId(nonExistentId)

        // should return null
        assertThat(doctors, nullValue())
    }

    @Test
    internal fun `check getByEventId returns an empty list if the event exists but has no doctors`() = runTest {

        val doctorlessEyeExam = EventMother.eyeExamPersisted.apply { doctors.clear() }

        // the event exists but has no doctors
        coEvery { readEventService.getByEventId(EventMother.eyeExamDto.id) } returns doctorlessEyeExam

        val readDoctorServiceImpl = ReadDoctorServiceImpl(doctorRepository, readPatientService, readEventService)

        // go get the doctors attached to that event
        val doctors = readDoctorServiceImpl.getByEventId(EventMother.eyeExamDto.id)

        // should return an empty list
        assertThat(doctors, equalTo(emptyList()))
    }

    @Test
    internal fun `check getByEventId returns an list of one element if the event exists and has a doctor`() = runTest {
        val eyeExamAttendedByJenny = EventMother.eyeExamPersisted.apply {
            doctors.clear()
            doctors.add(DoctorEventEntity(DoctorMother.jennyTheDoctorPersisted, this))
        }

        // the event exists and has a single doctor
        coEvery { readEventService.getByEventId(EventMother.eyeExamDto.id) } returns eyeExamAttendedByJenny

        val readDoctorServiceImpl = ReadDoctorServiceImpl(doctorRepository, readPatientService, readEventService)

        // go get the doctors attached to that event
        val doctors = readDoctorServiceImpl.getByEventId(EventMother.eyeExamDto.id)

        // should return a list containing that doctor
        assertThat(doctors, equalTo(listOf(DoctorMother.jennyTheDoctorPersisted)))
    }
}