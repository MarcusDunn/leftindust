package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.dao.entity.Visit
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VisitQueryTest {
    private val visitDao = mockk<VisitDao>()
    private val eventDao = mockk<EventDao>()

    private val graphQLAuthContext = mockk<GraphQLAuthContext>()

    @Test
    fun getVisitsByPatient() {
        val patientID = UUID.randomUUID()
        val eventID = UUID.randomUUID()
        val visitID = UUID.randomUUID()

        every { graphQLAuthContext.mediqAuthToken } returns mockk()

        val mockkEvent = mockk<Event> {
            every { id } returns eventID
        }

        every { eventDao.getPatientEvents(GraphQLPatient.ID(patientID), any()) } returns listOf(mockkEvent)

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }

        every { visitDao.findByEvent(GraphQLEvent.ID(eventID), any()) } returns mockkVisit

        val visitQuery = VisitQuery(visitDao, eventDao)
        val result = runBlocking {
            visitQuery.visits(
                pid = GraphQLPatient.ID(patientID),
                graphQLAuthContext = graphQLAuthContext
            )
        }

        assertEquals(listOf(GraphQLVisit(mockkVisit, graphQLAuthContext)), result)
    }

    @Test
    fun `get visits by doctor`() {
        val doctorID = UUID.randomUUID()
        val eventID = UUID.randomUUID()
        val visitID = UUID.randomUUID()

        every { graphQLAuthContext.mediqAuthToken } returns mockk()

        val mockkEvent = mockk<Event> {
            every { id } returns eventID
        }

        every { eventDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns listOf(mockkEvent)

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }

        every { visitDao.findByEvent(GraphQLEvent.ID(eventID), any()) } returns mockkVisit

        val visitQuery = VisitQuery(visitDao, eventDao)

        val result =
            runBlocking { visitQuery.visits(did = GraphQLDoctor.ID(doctorID), graphQLAuthContext = graphQLAuthContext) }

        assertEquals(listOf(GraphQLVisit(mockkVisit, graphQLAuthContext)), result)

    }

    @Test
    fun `get visit by vids`() {
        val visitID = UUID.randomUUID()

        every { graphQLAuthContext.mediqAuthToken } returns mockk()
        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitID
        }
        every { graphQLAuthContext.mediqAuthToken } returns mockk()
        every { visitDao.getVisitByVid(GraphQLVisit.ID(visitID), any()) } returns mockkVisit

        val visitQuery = VisitQuery(visitDao, eventDao)

        val result =
            runBlocking {
                visitQuery.visits(
                    vids = listOf(GraphQLVisit.ID(visitID)),
                    graphQLAuthContext = graphQLAuthContext
                )
            }

        assertEquals(listOf(GraphQLVisit(mockkVisit, graphQLAuthContext)), result)
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

        every { graphQLAuthContext.mediqAuthToken } returns mockk()
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

        every { eventDao.getPatientEvents(GraphQLPatient.ID(patientID), any()) } returns listOf(
            mockkEvent1,
            mockkEvent3
        )

        every { visitDao.findByEvent(GraphQLEvent.ID(eventID1), any()) } returns mockkVisit1

        every { eventDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns listOf(mockkEvent2)

        every { visitDao.findByEvent(GraphQLEvent.ID(eventID2), any()) } returns mockkVisit2

        every { visitDao.findByEvent(GraphQLEvent.ID(eventID3), any()) } returns mockkVisit3

        val visitQuery = VisitQuery(visitDao, eventDao)

        val result = runBlocking {
            visitQuery.visits(
                did = GraphQLDoctor.ID(doctorID),
                pid = GraphQLPatient.ID(patientID),
                graphQLAuthContext = graphQLAuthContext
            )
        }

        assertEquals(
            listOf(mockkVisit1, mockkVisit3, mockkVisit2)
                .map { GraphQLVisit(it, graphQLAuthContext) },
            result
        )
    }
}