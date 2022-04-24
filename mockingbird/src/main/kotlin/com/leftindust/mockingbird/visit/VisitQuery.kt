package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
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
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLVisit> = when {
        vids != null -> vids.map { vid ->
            visitDao.getVisitByVid(vid, dataFetchingEnvironment.authToken)
        }.map { GraphQLVisit(it) }
        pid != null && did != null -> {
            val patientVisits = visits(pid = pid, dataFetchingEnvironment = dataFetchingEnvironment)
            val doctorVisits = visits(did = did, dataFetchingEnvironment = dataFetchingEnvironment)
            (patientVisits + doctorVisits).distinctBy { it.vid }
        }
        pid != null -> withContext(Dispatchers.IO) {
            eventDao.getPatientEvents(pid, dataFetchingEnvironment.authToken)
        }
            .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), dataFetchingEnvironment.authToken) }
            .map { GraphQLVisit(it) }
        did != null -> withContext(Dispatchers.IO) {
            eventDao.getByDoctor(did, dataFetchingEnvironment.authToken)
        }
            .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), dataFetchingEnvironment.authToken) }
            .map { GraphQLVisit(it) }
        else -> throw GraphQLKotlinException("invalid arguments to visits")
    }
}