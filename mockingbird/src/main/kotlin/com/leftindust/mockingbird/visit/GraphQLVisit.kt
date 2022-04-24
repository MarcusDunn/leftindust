package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.patient.ReadPatientDao
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLName("Visit")
data class GraphQLVisit(
    val vid: ID,
    val title: String? = null,
    val description: String? = null,
) {
    @GraphQLName("VisitId")
    data class ID(val id: UUID)

    constructor(visit: Visit) : this(
        vid = ID(visit.id!!),
        title = visit.title,
        description = visit.description,
    )

    suspend fun event(
        @GraphQLIgnore @Autowired eventDao: EventDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.getEventVisit(vid, dataFetchingEnvironment.authToken)
    }.let(::GraphQLEvent)

    suspend fun patients(
        @GraphQLIgnore @Autowired readPatientDao: ReadPatientDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        readPatientDao.getVisitPatients(vid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPatient)
}