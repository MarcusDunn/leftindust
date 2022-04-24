package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.patient.GraphQLPatient

interface VisitDao {
    @Blocking
    fun getVisitByVid(vid: GraphQLVisit.ID, requester: MediqToken): Visit

    @Blocking
    fun addVisit(visitInput: GraphQLVisitInput, requester: MediqToken): Visit

    @Blocking
    fun findByEvent(eid: GraphQLEvent.ID, requester: MediqToken): Visit?

    @Blocking
    fun getPatientVisits(pid: GraphQLPatient.ID, requester: MediqToken): List<Visit>

    @Blocking
    fun editVisit(visit: GraphQLVisitEditInput, requester: MediqToken): Visit
}