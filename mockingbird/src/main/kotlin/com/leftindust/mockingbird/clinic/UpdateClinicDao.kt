package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action

interface UpdateClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.UPDATE to Tables.Clinic))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun editClinic(clinic: GraphQLClinicEditInput, requester: MediqToken): Clinic
}