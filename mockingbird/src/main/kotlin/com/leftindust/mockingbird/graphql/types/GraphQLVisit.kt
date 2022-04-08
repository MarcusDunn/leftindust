package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.dao.entity.Visit
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import com.leftindust.mockingbird.graphql.types.icd.GraphQLFoundationIcdCode
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("Visit")
data class GraphQLVisit(
    val vid: ID,
    val title: String? = null,
    val description: String? = null,
    private val authContext: GraphQLAuthContext,
) {
    @GraphQLName("VisitId")
    data class ID(val id: UUID)

    constructor(visit: Visit, graphQLAuthContext: GraphQLAuthContext) : this(
        vid = ID(visit.id!!),
        title = visit.title,
        description = visit.description,
        authContext = graphQLAuthContext,
    )

    suspend fun foundationIcdUrls(@GraphQLIgnore @Autowired visitDao: VisitDao): List<GraphQLFoundationIcdCode> {
        TODO("")
    }

    suspend fun event(
        @GraphQLIgnore @Autowired eventDao: EventDao
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.getEventVisit(vid, authContext.mediqAuthToken)
    }.let { GraphQLEvent(it, authContext) }

    suspend fun patients(
        @GraphQLIgnore @Autowired readPatientDao: ReadPatientDao
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        readPatientDao.getVisitPatients(vid, authContext.mediqAuthToken)
    }.map { GraphQLPatient(it, authContext) }
}