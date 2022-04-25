package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action

interface DeletePatientDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Patient))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun removeByPID(pid: GraphQLPatient.ID, requester: MediqToken): Patient
}