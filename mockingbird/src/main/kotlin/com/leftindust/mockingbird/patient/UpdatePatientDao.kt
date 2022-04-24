package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate

interface UpdatePatientDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.UPDATE to Tables.Patient))
    }

    override fun necessaryPermissions() = necessaryPermissions

    @Blocking
    fun addDoctorToPatient(pid: GraphQLPatient.ID, did: GraphQLDoctor.ID, requester: MediqToken): Patient

    @Blocking
    fun update(patientInput: GraphQLPatientEditInput, requester: MediqToken): Patient

    @Blocking
    fun assignForms(patients: List<GraphQLPatient.ID>, survey: GraphQLFormTemplate.ID, requester: MediqToken): Collection<Patient>
}

