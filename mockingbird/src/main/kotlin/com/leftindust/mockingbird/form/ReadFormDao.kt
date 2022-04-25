package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput

interface ReadFormDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Form))
    }

    override fun necessaryPermissions() = necessaryPermissions

    @Blocking
    fun getByIds(ids: List<GraphQLFormTemplate.ID>, requester: MediqToken): Collection<Form>

    @Blocking
    fun getMany(range: GraphQLRangeInput, requester: MediqToken): List<Form>

    @Blocking
    fun getByPatientAssigned(patient: GraphQLPatient.ID, requester: MediqToken): Collection<AssignedForm>
}