package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class VisitQuery(
    @Autowired private val visitDao: VisitDao,
    @Autowired private val eventDao: EventDao,
) : Query {
    suspend fun visits(
        vids: List<GraphQLVisit.ID>? = null,
        pid: GraphQLPatient.ID? = null,
        did: GraphQLDoctor.ID? = null,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLVisit> {
        return when {
            vids != null -> vids.map { vid ->
                visitDao
                    .getVisitByVid(vid, graphQLAuthContext.mediqAuthToken)
            }.map { GraphQLVisit(it, graphQLAuthContext) }
            pid != null && did != null -> {
                val patientVisits = visits(pid = pid, graphQLAuthContext = graphQLAuthContext)
                val doctorVisits = visits(did = did, graphQLAuthContext = graphQLAuthContext)
                (patientVisits + doctorVisits).distinctBy { it.vid }
            }
            pid != null -> withContext(Dispatchers.IO) {
                eventDao.getPatientEvents(pid, graphQLAuthContext.mediqAuthToken)
            }
                .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), graphQLAuthContext.mediqAuthToken) }
                .map { GraphQLVisit(it, graphQLAuthContext) }
            did != null -> withContext(Dispatchers.IO) {
                eventDao.getByDoctor(did, graphQLAuthContext.mediqAuthToken)
            }
                .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), graphQLAuthContext.mediqAuthToken) }
                .map { GraphQLVisit(it, graphQLAuthContext) }
            else -> throw GraphQLKotlinException("invalid arguments to visits")
        }
    }
}