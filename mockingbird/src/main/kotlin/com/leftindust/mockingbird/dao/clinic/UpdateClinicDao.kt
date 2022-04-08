package com.leftindust.mockingbird.dao.clinic

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.Clinic
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput

interface UpdateClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.UPDATE to Tables.Clinic))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun editClinic(clinic: GraphQLClinicEditInput, requester: MediqToken): Clinic
}