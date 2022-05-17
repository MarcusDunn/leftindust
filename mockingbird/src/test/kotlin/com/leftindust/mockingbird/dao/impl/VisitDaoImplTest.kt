package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.event.HibernateEventRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.visit.HibernateVisitRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.visit.VisitDto
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCodeInput
import com.leftindust.mockingbird.visit.GraphQLVisitInput
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.hibernate.SessionFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VisitDaoImplTest {
    private val authorizer = mockk<Authorizer>()
    private val eventRepository = mockk<HibernateEventRepository>()
    private val visitRepository = mockk<HibernateVisitRepository>()
    private val doctorRepository = mockk<DoctorRepository>()
    private val patientRepository = mockk<HibernatePatientRepository>()
    private val sessionFactory = mockk<SessionFactory>()

    @Test
    fun getVisitByVid() {
        val mockkVisit = mockk<Visit>()

        val visitID = UUID.randomUUID()

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        every { visitRepository.getById(visitID) } returns mockkVisit

        val visitDaoImpl = VisitDaoImpl(
            authorizer,
            eventRepository,
            visitRepository,
            sessionFactory,
            patientRepository
        )

        val result = visitDaoImpl.getVisitByVid(VisitDto.VisitDtoId(visitID), mockk())

        assertEquals(mockkVisit, result)
    }

    @Test
    fun addVisit() {
        val doctorID = UUID.randomUUID()
        val patientID = UUID.randomUUID()
        val eventID = UUID.randomUUID()

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { doctorRepository.getById(doctorID) } returns mockk {
            every { id } returns doctorID
            every { events } returns mutableSetOf()
        }
        every { patientRepository.getById(patientID) } returns mockk {
            every { id } returns patientID
            every { events } returns mutableSetOf()
        }

        every { eventRepository.getById(eventID) } returns mockk()

        val mockkVisit = mockk<Visit>()

        val visitDaoImpl = VisitDaoImpl(
            authorizer, eventRepository, visitRepository, sessionFactory,
            patientRepository
        )

        val visitInput = mockk<GraphQLVisitInput>(relaxed = true) {
            every { eid } returns EventDto.EventDtoId(eventID)
            every { foundationIcdCodes } returns listOf(GraphQLFoundationIcdCodeInput("1222121"))
        }

        every { visitRepository.save(any()) } returns mockkVisit

        val result = visitDaoImpl.addVisit(visitInput, mockk())

        assertEquals(mockkVisit, result)
    }
}