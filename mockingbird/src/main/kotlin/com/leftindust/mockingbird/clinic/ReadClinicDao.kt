package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.doctor.GraphQLDoctor

interface ReadClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Clinic))
    }

    override fun necessaryPermissions() = necessaryPermissions

    @Blocking
    fun getByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): Collection<Clinic>
    @Blocking
    fun getByCid(cid: GraphQLClinic.ID, requester: MediqToken): Clinic
}