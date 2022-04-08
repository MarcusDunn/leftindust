package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLTimeRangeInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class EventQuery(
    private val eventDao: EventDao,
) : Query {
    @GraphQLDescription(
        """
        Gets a list of events by a list of thier ID's. Will error if any of the ID's us not found.
    """
    )
    suspend fun eventsByIds(events: List<GraphQLEvent.ID>, graphQLAuthContext: GraphQLAuthContext) = events
        .map { eventDao.getById(it, graphQLAuthContext.mediqAuthToken) }
        .map { GraphQLEvent(it, graphQLAuthContext) }

    @GraphQLDescription(
        """
        Gets a list of events by a range of time that returned the events will occur within.
    """
    )
    suspend fun eventsByTimeRange(
        range: GraphQLTimeRangeInput,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        eventDao.getBetween(range, graphQLAuthContext.mediqAuthToken)
    }.map { GraphQLEvent(it, graphQLAuthContext) }

    @GraphQLDescription(
        """
        Gets a list of events shared by the doctors and patients. ie. all events where both the doctor and the patient attend.
    """
    )
    suspend fun eventsByDoctorsAndPatients(
        patients: List<GraphQLPatient.ID>,
        doctors: List<GraphQLDoctor.ID>,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLEvent> = coroutineScope {
        val patientEvents = async { eventsByPatient(patients, graphQLAuthContext) }
        val doctorEvents = async { eventsByDoctor(doctors, graphQLAuthContext) }

        patientEvents.await().intersect(doctorEvents.await().toSet()).toList()
    }

    suspend fun eventsByPatient(
        patients: List<GraphQLPatient.ID>,
        graphQLAuthContext: GraphQLAuthContext,
    ): List<GraphQLEvent> = patients
        .map { eventDao.getPatientEvents(it, graphQLAuthContext.mediqAuthToken) }
        .flatMap { events -> events.map { GraphQLEvent(it, graphQLAuthContext) } }

    suspend fun eventsByDoctor(
        doctors: List<GraphQLDoctor.ID>,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLEvent> = doctors
        .map { eventDao.getByDoctor(it, graphQLAuthContext.mediqAuthToken) }
        .flatMap { events -> events.map { GraphQLEvent(it, graphQLAuthContext) } }
}