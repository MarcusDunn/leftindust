package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class VisitQuery(
    @Autowired private val visitDao: VisitDao,
    @Autowired private val eventDao: EventDao,
) : Query {
    @Deprecated("removed due to lack of query verification", replaceWith = ReplaceWith("visitsById"))
    suspend fun visits(
        vids: List<GraphQLVisit.ID>? = null,
        pid: GraphQLPatient.ID? = null,
        did: GraphQLDoctor.ID? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLVisit> = when {
        vids != null -> visitsById(vids, dataFetchingEnvironment)
        pid != null && did != null -> visitByPatientOrDoctor(pid, dataFetchingEnvironment, did)
        pid != null -> visitsByPatient(pid, dataFetchingEnvironment)
        did != null -> visitsByDoctor(did, dataFetchingEnvironment)
        else -> throw GraphQLKotlinException("invalid arguments to visits")
    }

    fun visitsByDoctor(
        did: GraphQLDoctor.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = eventDao.getByDoctor(did, dataFetchingEnvironment.authToken)
        .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), dataFetchingEnvironment.authToken) }
        .map { GraphQLVisit(it) }

    fun visitsByPatient(
        pid: GraphQLPatient.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = eventDao
        .getPatientEvents(pid, dataFetchingEnvironment.authToken)
        .mapNotNull { visitDao.findByEvent(GraphQLEvent.ID(it.id!!), dataFetchingEnvironment.authToken) }
        .map { GraphQLVisit(it) }

    suspend fun visitByPatientOrDoctor(
        pid: GraphQLPatient.ID,
        dataFetchingEnvironment: DataFetchingEnvironment,
        did: GraphQLDoctor.ID
    ): List<GraphQLVisit> {
        val patientVisits = visitsByPatient(pid, dataFetchingEnvironment)
        val doctorVisits = visitsByDoctor(did, dataFetchingEnvironment)
        return (patientVisits + doctorVisits).distinctBy { it.vid }
    }

    fun visitsById(
        vids: List<GraphQLVisit.ID>,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = vids.map { vid ->
        visitDao.getVisitByVid(vid, dataFetchingEnvironment.authToken)
    }.map { GraphQLVisit(it) }
}