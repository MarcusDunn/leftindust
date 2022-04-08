package com.leftindust.mockingbird.dao.clinic

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.Clinic
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor

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