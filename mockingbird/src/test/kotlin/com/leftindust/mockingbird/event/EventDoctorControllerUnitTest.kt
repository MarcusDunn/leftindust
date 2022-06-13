package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.doctor.DoctorToDoctorDtoConverter
import com.leftindust.mockingbird.doctor.LocalDateToLocalDateDtoConverter
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.EventMother
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class EventDoctorControllerUnitTest {

    @MockK
    private lateinit var readDoctorService: ReadDoctorService // use a mock ReadDoctorService
    private val doctorToDoctorDtoConverter = DoctorToDoctorDtoConverter(LocalDateToLocalDateDtoConverter()) // use a real DoctorToDoctorDtoConverter

    @Test
    internal fun `check querying doctors from an event returns an list of a single element if the event has one doctors`() = runTest {
        val eventDoctorController = EventDoctorController(readDoctorService, doctorToDoctorDtoConverter)

        val eyeExam = EventMother.eyeExamDto

        val doctor = DoctorMother.jennyTheDoctorPersisted

        coEvery { readDoctorService.getByEventId(eyeExam.id) } returns listOf(doctor)

        val doctors = eventDoctorController.doctors(eyeExam)

        assertThat(doctors, equalTo(listOf(DoctorMother.jennyTheDoctorDto)))
    }

    @Test
    internal fun `check querying doctors from an event returns an empty list if the event has no doctors`() = runTest {
        val eventDoctorController = EventDoctorController(readDoctorService, doctorToDoctorDtoConverter)

        val eyeExam = EventMother.eyeExamDto

        coEvery { readDoctorService.getByEventId(eyeExam.id) } returns emptyList()

        val doctors = eventDoctorController.doctors(eyeExam)

        assertThat(doctors, equalTo(emptyList()))
    }

    @Test
    internal fun `check querying doctors from an event throws a nullSubQueryExeption if the event does not exist`() = runTest {
        val eventDoctorController = EventDoctorController(readDoctorService, doctorToDoctorDtoConverter)

        val eyeExam = EventMother.eyeExamDto

        coEvery { readDoctorService.getByEventId(eyeExam.id) } returns null

        assertThrows<NullSubQueryException> {
            eventDoctorController.doctors(eyeExam)
        }
    }
}