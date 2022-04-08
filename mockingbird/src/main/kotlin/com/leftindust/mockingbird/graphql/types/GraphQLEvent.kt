package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("Event")
data class GraphQLEvent(
    val eid: ID,
    val title: String,
    val description: String?,
    val startTime: GraphQLUtcTime?,
    val endTime: GraphQLUtcTime?,
    val allDay: Boolean,
    val reoccurrence: GraphQLRecurrence?,
    private val authContext: GraphQLAuthContext,
) {
    @GraphQLName("EventId")
    data class ID(val id: UUID)

    // we need the `senseless comparison` because hibernate handles nullability for embedded fields
    // (such as reoccurrence) in the most unintuitive way possible. reoccurrence CANNOT BE NULL instead hibernate sets
    // all of its fields to null because hibernate hates you and would kill your dog if it had the chance.
    @Suppress("SENSELESS_COMPARISON")
    constructor(event: Event, authContext: GraphQLAuthContext) : this(
        eid = event.id?.let { ID(it) } ?: throw NullPointerException("id cannot be null when creating a GraphQLEvent"),
        title = event.title,
        description = event.description,
        startTime = GraphQLUtcTime(event.startTime),
        endTime = event.endTime?.let { GraphQLUtcTime(it) },
        allDay = event.allDay,
        reoccurrence = event.reoccurrence?.let {
            if (it.endDate != null && it.startDate != null && it.days != null) {
                GraphQLRecurrence(it)
            } else {
                null
            }
        },
        authContext = authContext
    )

    suspend fun doctors(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao
    ): List<GraphQLDoctor> = withContext(Dispatchers.IO) {
        doctorDao.getByEvent(eid, authContext.mediqAuthToken)
    }.map { GraphQLDoctor(it, authContext) }

    suspend fun patients(
        @GraphQLIgnore @Autowired patientDao: ReadPatientDao
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getByEvent(eid, authContext.mediqAuthToken)
    }.map { GraphQLPatient(it, authContext) }

    suspend fun visit(
        @GraphQLIgnore @Autowired visitDao: VisitDao
    ): GraphQLVisit? = withContext(Dispatchers.IO) {
        visitDao.findByEvent(eid, authContext.mediqAuthToken)
    }?.let { GraphQLVisit(it, authContext) }
}