package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.visit.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput

interface ReadPatientDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Patient))
    }

    override fun necessaryPermissions() = necessaryPermissions

    @Blocking
    fun getByPID(pid: GraphQLPatient.ID, requester: MediqToken): Patient

    @Blocking
    fun getByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): Collection<Patient>

    @Blocking
    fun getVisitPatients(vid: GraphQLVisit.ID, requester: MediqToken): Collection<Patient>

    @Blocking
    fun getMany(
        range: GraphQLRangeInput,
        sortedBy: Patient.SortableField = Patient.SortableField.PID,
        requester: MediqToken
    ): Collection<Patient>

    @Blocking
    fun getByEvent(eid: GraphQLEvent.ID, requester: MediqToken): Collection<Patient>

    @Blocking
    fun getPatientsByPids(pids: List<GraphQLPatient.ID>, requester: MediqToken): Collection<Patient>

    @Blocking
    fun searchByExample(example: GraphQLPatientExample, requester: MediqToken): Collection<Patient>

    @Blocking
    fun getByUser(uid: String, requester: MediqToken): Patient?
}