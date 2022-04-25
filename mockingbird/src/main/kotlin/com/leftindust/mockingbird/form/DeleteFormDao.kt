package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables

interface DeleteFormDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.DELETE to Tables.Form))
    }

    override fun necessaryPermissions() = necessaryPermissions
    @Blocking
    fun deleteForm(form: GraphQLFormTemplate.ID, requester: MediqToken): Form
}