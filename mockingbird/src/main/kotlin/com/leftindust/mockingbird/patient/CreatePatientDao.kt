package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action

interface CreatePatientDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.CREATE to Tables.Patient))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun addNewPatient(patient: GraphQLPatientInput, requester: MediqToken): Patient
}