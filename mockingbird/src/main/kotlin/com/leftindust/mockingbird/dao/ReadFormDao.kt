package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.AssignedForm
import com.leftindust.mockingbird.dao.entity.Form
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput

interface ReadFormDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.READ to Tables.Form))
    }

    override fun necessaryPermissions() = necessaryPermissions

    @Blocking
    fun getByIds(ids: List<GraphQLFormTemplate.ID>, requester: MediqToken): Collection<Form>

    @Blocking
    fun getMany(range: GraphQLRangeInput, requester: MediqToken): List<Form>

    @Blocking
    fun getByPatientAssigned(patient: GraphQLPatient.ID, requester: MediqToken): Collection<AssignedForm>
}