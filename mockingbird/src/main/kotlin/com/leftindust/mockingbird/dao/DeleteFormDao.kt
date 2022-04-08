package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.Form
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate

interface DeleteFormDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.DELETE to Tables.Form))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun deleteForm(form: GraphQLFormTemplate.ID, requester: MediqToken): Form
}