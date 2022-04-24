package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action

interface CreateClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.CREATE to Tables.Clinic))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun addClinic(clinic: GraphQLClinicInput, requester: MediqToken): Clinic
}