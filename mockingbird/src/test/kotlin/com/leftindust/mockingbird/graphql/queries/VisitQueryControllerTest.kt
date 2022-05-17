package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.event.EventService
import com.leftindust.mockingbird.visit.VisitDao
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.visit.VisitDto
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import com.leftindust.mockingbird.visit.VisitQueryController
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class VisitQueryControllerTest {
    private val visitDao = mockk<VisitDao>()
    private val eventService = mockk<EventService>()

    @Test
    fun getVisitsByPatient() {
        val patientID = UUID.randomUUID()
        val eventID = UUID.randomUUID()
        val visitID = UUID.randomUUID()

        val mockkEvent = mockk<Event> {
            every { id } returns eventID
        }

        every { eventService.getPatientEvents(PatientDto.PatientDtoId(patientID), any()) } returns listOf(mockkEvent)

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }

        every { visitDao.findByEvent(EventDto.EventDtoId(eventID), any()) } returns mockkVisit

        val visitQueryController = VisitQueryController(visitDao, eventService)
        val result = runBlocking {
            visitQueryController.visits(
                pid = PatientDto.PatientDtoId(patientID),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(VisitDto(mockkVisit)), result)
    }

    @Test
    fun `get visits by doctor`() {
        val doctorID = UUID.randomUUID()
        val eventID = UUID.randomUUID()
        val visitID = UUID.randomUUID()

        val mockkEvent = mockk<Event> {
            every { id } returns eventID
        }

        every { eventService.getByDoctor(DoctorDto.DoctorDtoId(doctorID), any()) } returns listOf(mockkEvent)

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }

        every { visitDao.findByEvent(EventDto.EventDtoId(eventID), any()) } returns mockkVisit

        val visitQueryController = VisitQueryController(visitDao, eventService)

        val result = runBlocking {
            visitQueryController.visits(
                did = DoctorDto.DoctorDtoId(doctorID),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(VisitDto(mockkVisit)), result)

    }

    @Test
    fun `get visit by vids`() {
        val visitID = UUID.randomUUID()

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }

        every { visitDao.getByVisitId(VisitDto.VisitDtoId(visitID), any()) } returns mockkVisit

        val visitQueryController = VisitQueryController(visitDao, eventService)

        val result = runBlocking {
            visitQueryController.visits(
                vids = listOf(VisitDto.VisitDtoId(visitID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(VisitDto(mockkVisit)), result)
    }

    @Test
    fun `get visit by pid and did`() {
        val visitID1 = UUID.randomUUID()
        val visitID2 = UUID.randomUUID()
        val visitID3 = UUID.randomUUID()
        val eventID1 = UUID.randomUUID()
        val eventID2 = UUID.randomUUID()
        val eventID3 = UUID.randomUUID()
        val patientID = UUID.randomUUID()
        val doctorID = UUID.randomUUID()


        val mockkVisit1 = mockk<Visit>(relaxed = true) {
            every { id } returns visitID1
        }
        val mockkVisit2 = mockk<Visit>(relaxed = true) {
            every { id } returns visitID2
        }
        val mockkVisit3 = mockk<Visit>(relaxed = true) {
            every { id } returns visitID3
        }

        val mockkEvent1 = mockk<Event> {
            every { id } returns eventID1
        }

        val mockkEvent2 = mockk<Event> {
            every { id } returns eventID2
        }

        val mockkEvent3 = mockk<Event> {
            every { id } returns eventID3
        }

        every { eventService.getPatientEvents(PatientDto.PatientDtoId(patientID), any()) } returns listOf(
            mockkEvent1,
            mockkEvent3
        )

        every { visitDao.findByEvent(EventDto.EventDtoId(eventID1), any()) } returns mockkVisit1

        every { eventService.getByDoctor(DoctorDto.DoctorDtoId(doctorID), any()) } returns listOf(mockkEvent2)

        every { visitDao.findByEvent(EventDto.EventDtoId(eventID2), any()) } returns mockkVisit2

        every { visitDao.findByEvent(EventDto.EventDtoId(eventID3), any()) } returns mockkVisit3

        val visitQueryController = VisitQueryController(visitDao, eventService)

        val result = runBlocking {
            visitQueryController.visits(
                did = DoctorDto.DoctorDtoId(doctorID),
                pid = PatientDto.PatientDtoId(patientID),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(
            listOf(mockkVisit1, mockkVisit3, mockkVisit2)
                .map { VisitDto(it) },
            result
        )
    }
}