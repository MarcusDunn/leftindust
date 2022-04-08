package com.leftindust.mockingbird.dao.patient

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.graphql.types.GraphQLPatient

interface DeletePatientDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Patient))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun removeByPID(pid: GraphQLPatient.ID, requester: MediqToken): Patient
}